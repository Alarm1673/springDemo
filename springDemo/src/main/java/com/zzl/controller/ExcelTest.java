package com.zzl.controller;

import com.zzl.controller.demo.ExcelUtils;
import com.zzl.controller.demo.User;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class <code>Object</code> is the root of the class hierarchy.
 * Every class has <code>Object</code> as a superclass. All objects,
 * including arrays, implement the methods of this class.
 *
 * @author Administrator
 * @version 1.0
 * @see
 * @since JDK1.7
 * <p>
 * History
 * Created by Administrator on 2017/1/20 0020.
 */
@RestController
@RequestMapping("excel")
public class ExcelTest {

    private static final Logger logger = Logger.getLogger(ExcelTest.class);

    @RequestMapping("/export")
    public void export(HttpServletResponse response)throws Exception{
        User user = new User();
        user.setName("张山");
        user.setSex("男");
        User user1 = new User();
        user1.setName("李四");
        user1.setSex("女");
        List<User> list = new ArrayList<User>();
        list.add(user);
        list.add(user1);
        Map<String,Object> data = new HashedMap();
        data.put("list",list);
        String fileName = "demo";
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(), "ISO8859-1") + ".xlsx");
        response.setContentType("application/vnd.ms-excel");
        try {
            XLSTransformer xlsTransformer = new XLSTransformer();
            File tmpFile = File.createTempFile("DealDTO", ".xlsx");
            String tplName = "jxls/demo.xlsx";
            String templatePath = Thread.currentThread().getContextClassLoader().getResource(tplName).getPath();
            xlsTransformer.transformXLS(templatePath, data, tmpFile.getAbsolutePath());
            ExcelUtils.down(tmpFile.getAbsolutePath(), response, fileName);
        } catch (Exception e) {
            logger.error("export error", e);
        }
    }
}
