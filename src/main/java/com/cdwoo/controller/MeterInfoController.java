/**
 * 
 */
package com.cdwoo.controller;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdwoo.common.CDPage;
import com.cdwoo.common.CDParam;
import com.cdwoo.common.CDResult;
import com.cdwoo.common.Constants;
import com.cdwoo.entity.MeterInfoParam;
import com.cdwoo.entity.MeterInfoStatisticParam;
import com.cdwoo.entity.User;
import com.cdwoo.service.MeterInfoService;
import com.cdwoo.utils.ExcelUtil;

/**
 * @author cd
 *
 */
@Controller
@RequestMapping("meterinfo")
public class MeterInfoController {
	@Autowired
	private MeterInfoService meterInfoService;
	
	@ResponseBody
	@RequestMapping("queryMeterinfoByPage")
	public Object queryMeterInfoByPage (MeterInfoParam param, HttpServletRequest req) {
		if (req.getSession().getAttribute(Constants.USER_CONTEXT) == null) {
			return CDResult.fail("login time out");
		}
		String devices = req.getParameter("devices");
		param.setCompanyId(((User)req.getSession().getAttribute(Constants.USER_CONTEXT)).getCompanyId());
		param.setRoleId(((User)req.getSession().getAttribute(Constants.USER_CONTEXT)).getRoleId());
		return CDResult.success(meterInfoService.queryMeterInfoByPage(param));
	}
	
	@ResponseBody
	@RequestMapping("queryStatisticMeterInfo")
	public CDResult queryStatisticMeterInfo(MeterInfoStatisticParam param, HttpServletRequest req) {
		if (req.getSession().getAttribute(Constants.USER_CONTEXT) == null) {
			return CDResult.fail("登录超时，请重新登录");
		}
		param.setCompanyId(((User)req.getSession().getAttribute(Constants.USER_CONTEXT)).getCompanyId());
		return CDResult.success(meterInfoService.queryStatisticMeterInfo(param));
	}
	
    /**
     * 导出报表
     * @return
     */
    @RequestMapping(value = "/export")
    @ResponseBody
    public void export(MeterInfoParam param, HttpServletRequest request,HttpServletResponse response) throws Exception {
    	   if (request.getSession().getAttribute(Constants.USER_CONTEXT) == null) {
    		   response.sendRedirect("../jsp/login.jsp");
    		   return;
		   }
	       param.setCompanyId(((User)request.getSession().getAttribute(Constants.USER_CONTEXT)).getCompanyId());
		   param.setRoleId(((User)request.getSession().getAttribute(Constants.USER_CONTEXT)).getRoleId());
           //获取数据
           List<Map<String, Object>> list = meterInfoService.getExportData(param);
           //excel标题
           String[] title = URLDecoder.decode(request.getParameter("title"), "utf-8").split(",");
           String[] name = request.getParameter("names").split(",");
           //excel文件名
           String fileName = "电表数据"+System.currentTimeMillis()+".xls";

           //sheet名
           String sheetName = "数据表";
           String [][] content = new String[list.size()][];
           for (int i = 0; i < list.size(); i++) {
	            content[i] = new String[title.length];
				Map<String, Object> obj = list.get(i);
				for (int m = 0;m<name.length;m++) {
					content[i][m] = String.valueOf(obj.get(name[m]));
				}
           }

           //创建HSSFWorkbook 
           HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);
           //响应到客户端
           try {
               this.setResponseHeader(response, fileName);
               OutputStream os = response.getOutputStream();
               wb.write(os);
               os.flush();
               os.close();
           } catch (Exception e) {
        	   e.printStackTrace();
    	   }
    }
    //发送响应流方法
    public void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(),"utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
}
