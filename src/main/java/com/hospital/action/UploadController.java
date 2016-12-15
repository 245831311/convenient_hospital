package com.hospital.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hospital.constants.AppConstants;
import com.hospital.service.IUploadService;

/**
 * 用户控制器
 * @author yubing
 *
 */
@Controller
@RequestMapping("/uploadController")
public class UploadController extends BaseController{
	private Logger logger = Logger.getLogger(this.getClass());
	@Resource(name="uploadService")
	private IUploadService uploadService;
	
	@RequestMapping(value = "/importMeetingFileForCache", method = RequestMethod.POST)
	public void importMeetingFileForCache(){
		String tempPath = AppConstants.IMAGE_UPLOAD_DIR;
		try {
			Map<String,String> result = uploadService.commonImport(request,tempPath);
			
			if(MapUtils.isNotEmpty(result)){
				String fileName=result.get("fileName"), fileType=result.get("fileType"), 
						fileSize=result.get("fileSize"), filePath=result.get("filePath");
				uploadService.addUploadLogForCache(fileName, fileType, fileSize, filePath);
			}
		} catch (Exception e) {
			logger.error("[Controller.UploadController] importMeetingFileForCache occur exception!",e);
		}finally{
			try {
				notifyEnd(response.getWriter());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
     * 通知页面导入结束
     * 
     * @param writer
     * @param s
     * @param log
     */
    private void notifyEnd(PrintWriter writer) {
        writer.write("<script type='text/javascript'>");
        writer.write("parent.finishImport();");
        writer.write("</script>");
        writer.flush();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
    }
}
