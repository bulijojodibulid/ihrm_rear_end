package com.yk.Service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yk.Common.AssemblyUser;
import com.yk.DTO.ImportUserDTO;
import com.yk.Mapper.DepartmentMapper;
import com.yk.Mapper.EmpMapper;
import com.yk.Mapper.UserMapper;
import com.yk.Service.EmployeeService;
import com.yk.Utils.Template;
import com.yk.VO.EmployeeVO;
import com.yk.VO.PageBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service
public class employeeServiceImpl implements EmployeeService {
    private final DepartmentMapper departmentMapper;
    private final UserMapper userMapper;
    private final EmpMapper empMapper;
    private final AssemblyUser assemblyUser;
    public employeeServiceImpl(DepartmentMapper departmentMapper, UserMapper userMapper, EmpMapper empMapper, AssemblyUser assemblyUser){
        this.departmentMapper = departmentMapper;
        this.userMapper = userMapper;
        this.empMapper = empMapper;
        this.assemblyUser = assemblyUser;
    }

    @Override
    public PageBean<EmployeeVO> page(Integer page, Integer pageSize, String keyword, Integer departmentId) {
        // 1.定义列表存储所有部门ID
        List<Integer> ids = new LinkedList<>();
        // 2.定义队列，用于树形遍历算法
        Queue<Integer> queue = new LinkedList<>();
        // 3.将id正常存入列表中
        ids.add(departmentId);
        // 4.放入根节点
        queue.add(departmentId);
        // 5.执行算法
        getTreeId(ids, queue);

        if(page == null){
            page = 1;
        }
        if(pageSize == null){
            pageSize = 10;
        }
        PageHelper.startPage(page, pageSize);
        List<EmployeeVO> employees = userMapper.getEmployee(ids, keyword);
        Page<EmployeeVO> p = (Page<EmployeeVO>) employees;

        return new PageBean<>(p.getTotal(), p.getResult());
    }

    @Override
    public ByteArrayOutputStream export() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //获取所有员工信息
        List<EmployeeVO> employees = userMapper.getEmployee(null, null);

        Map<String, Method> map = new HashMap<String, Method>(){{
            put("用户名", EmployeeVO.class.getMethod("getName"));
            put("手机号", EmployeeVO.class.getMethod("getPhone"));
            put("部门", EmployeeVO.class.getMethod("getDepartmentName"));
            put("聘用形式", EmployeeVO.class.getMethod("getFormOfEmployment"));
            put("入职日期", EmployeeVO.class.getMethod("getTimeOfEntry"));
        }};
        String[] title = map.keySet().toArray(new String[0]);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        // 创建工作簿
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Data");

            // 创建标题
            Row row0 = sheet.createRow(0);
            Cell cell = row0.createCell(0);
            cell.setCellValue("人力资源项目用户批量导出");
            // 创建CellRangeAddress对象来合并区域，然后加入sheet中
            CellRangeAddress cellAddresses = new CellRangeAddress(0, 0, 0, 5);
            sheet.addMergedRegion(cellAddresses);

            // 设置子标题
            Row row1 = sheet.createRow(1);
            for(int i = 0; i < title.length; i++){
                Cell cel = row1.createCell(i);
                cel.setCellValue(title[i]);
            }
            // 循环遍历数据，存入工作簿中
            int dataRow = 2;
            for(int i = dataRow; i < employees.size()+dataRow; i++){
                EmployeeVO item = employees.get(i - dataRow);
                Row r = sheet.createRow(i);
                for(int j = 0; j < title.length; j++){
                    Cell c = r.createCell(j);
                    Method method = map.get(title[j]);
                    String data = method.invoke(item).toString();
                    if(title[j].equals("聘用形式")){
                        if(Objects.equals(data, "1")){
                            data = "正式";
                        }else {
                            data = "非正式";
                        }
                    }
                    c.setCellValue(data);
                }
            }
            // 写入文件
//            try (FileOutputStream outputStream = new FileOutputStream(new File("C:\\Users\\86189\\Desktop\\1.xlsx"))) {
//                workbook.write(outputStream);
//            }
            workbook.write(out);
        }
        return out;
    }

    @Override
    public ByteArrayOutputStream getTemplate() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Workbook workbook = Template.getTemplate();
        workbook.write(out);
        return out;
    }

    /**
     * 从excel文件中导入数据
     * @param file  excel文件
     * @return  返回字符串，如果为空，则意味没有错误，否则有问题，字符串内容为错误原因
     */
    @Override
    public String importEmp(MultipartFile file) throws IOException {
        DataFormatter format = new DataFormatter();
        try(Workbook workbook = new XSSFWorkbook(file.getInputStream())){
            Sheet sheet = workbook.getSheet("template");
            if(sheet == null){
                return "不是模板文件";
            }
            Row titles = sheet.getRow(1);
            for(Row row: sheet){
                if(row.getRowNum() == 0 || row.getRowNum() == 1){
                    continue;
                }
                // 创建一个新的对象
                ImportUserDTO user = new ImportUserDTO();
                for(Cell cell: row){
                    // 获取当前所在列
                    int columnIndex = cell.getColumnIndex();
                    // 获取当前列的标题
                    String title = titles.getCell(columnIndex).getStringCellValue();
                    // 处理数据
                    String s = format.formatCellValue(cell).trim();
                    try {
                        String res = assemblyUser.assembly(user, s, title);
                        if(!Objects.equals(res, "")){
                            return res;
                        }
                    } catch (InvocationTargetException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
//                    if(title.startsWith("用户名")){
//                        user.setName(s);
//                    } else if (title.startsWith("手机号")) {
//                        // 查询手机号是否存在
//                        String res = userMapper.getPhoneByPhone(s);
//                        if(res != null){
//                            return "手机号码重复";
//                        }
//                        user.setPhone(s);
//                    } else if (title.startsWith("部门")) {
//                        // 查找部门Id
//                        Integer id = departmentMapper.getIdByName(s);
//                        if(id == null){
//                            return "部门不存在";
//                        }
//                        user.setDepartmentId(id);
//                    } else if (title.startsWith("聘用形式")) {
//                        Integer form = s.equals("正式") ? 1 : 0;
//                        user.setFormOfEmployment(form);
//                    } else if (title.startsWith("转正日期")) {
//                        LocalDate localDate = LocalDate.parse(s, DateTimeFormatter.ofPattern("MM/dd/yy"));
//                        user.setTimeOfEntry(localDate);
//                    }
                }
                userMapper.addUser(user);
            }
        }
        return "";
    }

    @Override
    public void delUser(Integer id) {
        // 如果角色是部门管理，先将部门的managerId设置为0
        departmentMapper.updateDepByManagerId(id);
        // 删除角色
        userMapper.delUser(id);
    }

    @Override
    public Integer addUser(ImportUserDTO user) {
        // 检查手机号是否重复
        String res = userMapper.getPhoneByPhone(user.getPhone());
        if(res != null){
            return 2;
        }
        return userMapper.addUser(user);
    }

    @Override
    public EmployeeVO getOneEmp(Integer id) {
        return empMapper.getOneEmp(id);
    }

    @Override
    public Integer updateEmp(EmployeeVO emp) {
        return empMapper.updateEmp(emp);
    }

    @Override
    public void updateRole(Integer empId, List<Integer> newRoleIds) {
        // 先查找员工目前所有角色
        List<Integer> oldRoleIds = empMapper.getRoleListIdsByEmpId(empId);
        // 如果新数组为空，则意味删除全部
        if(newRoleIds.get(0).equals(0)){
            if(!oldRoleIds.isEmpty()){
                empMapper.delRoles(oldRoleIds, empId);
            }
            return;
        }
        //去掉旧节点与新节点中重复的元素
        if(oldRoleIds.size() > newRoleIds.size()){
            duplicate(oldRoleIds, newRoleIds);
        }else{
            duplicate(newRoleIds, oldRoleIds);
        }

        // 如果旧数组有值，删除表中旧数组的值
        if(!oldRoleIds.isEmpty()){
            empMapper.delRoles(oldRoleIds, empId);
        }

        // 如果新数组有值，则添加
        if(!newRoleIds.isEmpty()){
            empMapper.addRoles(newRoleIds, empId);
        }
    }

    @Override
    public List<Integer> getRoleIds(Integer empId) {
        return empMapper.getRoleListIdsByEmpId(empId);
    }

    /**
     * 去掉两个列表中共同的元素，迭代短的集合
     * @param longList 长集合
     * @param shortList 短集合，算法会迭代它
     */
    private void duplicate(List<Integer> longList, List<Integer> shortList){
        Iterator<Integer> iterator = shortList.iterator();
        while (iterator.hasNext()){
            Integer value = iterator.next();
            if(longList.contains(value)){
                iterator.remove();
                longList.remove(value);
            }
        }
    }
    /**
     * 查询所有该部门id下的所有子部门id
     */
    private void getTreeId(List<Integer> ids, Queue<Integer> queue){
        if(queue.isEmpty()){
            return;
        }
        Integer head = queue.poll();
        // 数据库搜索所有pid为head的id
        List<Integer> cids = departmentMapper.getId(head);
        ids.addAll(cids);
        queue.addAll(cids);
        getTreeId(ids, queue);
    }
}
