package com.udacity.jwdnd.course1.cloudstorage.contoller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/file")
public class FileController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    public final static String TAG_ = "FileController";
    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
    }


    @PostMapping("/add")
    public String postFile(FileForm fileForm, Model model, HttpSession session){
        LOGGER.debug(TAG_ + " add method");
        String errorMsgStr = "";
        int userId = (int) session.getAttribute("userId");
        if(fileForm.getFileId()==null || fileForm.getFileId().equals("")){
            LOGGER.debug(TAG_ + " add new file method");
            if(!this.fileService.isDupicateFileName(userId, fileForm.getFileEntity().getOriginalFilename())){
                //not duplate
                int addRow =1;
                try {
                    addRow = this.fileService.addFile(fileForm.getFileEntity(), userId);
                }catch(Exception e){
                    addRow = -1;
                }
                if(addRow!=1){
                    LOGGER.debug(TAG_ + " add new file failed");
                    model.addAttribute("errorResult", true);
                    errorMsgStr = "New file failed to add";
                    model.addAttribute("errorResultMessage", errorMsgStr);
                }else{
                    LOGGER.debug(TAG_ +  " add new file success");
                    model.addAttribute("successResult", true);
                }
            }else{
                LOGGER.debug(TAG_ + " add new file failed");
                model.addAttribute("errorResult", true);
                errorMsgStr = "This file already exist";
                model.addAttribute("errorResultMessage", errorMsgStr);
            }

        }else{
            LOGGER.debug(TAG_ + " update/edit file method");
            int updateRow;
            try {
                updateRow = fileService.updateFile(fileForm.getFileEntity(), userId, Integer.parseInt(fileForm.getFileId()));
            }catch (Exception e){
                updateRow = -1;
            }
            if(updateRow != 1){
                model.addAttribute("errorResult", true);
                LOGGER.debug(TAG_ + " update/edit file failed");
                errorMsgStr = "File failed to update/edit";
                model.addAttribute("errorResultMessage", errorMsgStr);

            }else{
                model.addAttribute("successResult", true);
                LOGGER.debug(TAG_ +  " update/edit file success");
            }
        }
        model.addAttribute("fileForm", new FileForm());
        return "result";

    }
    @GetMapping("/delete")
    public String deleteFile(@RequestParam(name="fileId") String fileId, Model model){
        LOGGER.debug("Calling file controller delete method with fileId: " + fileId);
        int delRow = this.fileService.deleteFile(Integer.parseInt(fileId));
        if(delRow == 1){
            model.addAttribute("successResult", true);
        }else{
            model.addAttribute("errorResult", true);
        }
        return "result";
    }

    @GetMapping("/view")
    public ResponseEntity<byte[]> viewFile(@RequestParam(name="fileId") String fileId, Model model){
        File file = fileService.getFile(Integer.parseInt(fileId));
        //reference: https://bezkoder.com/spring-boot-upload-file-database/
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .contentLength(file.getFileData().length)
                .body(file.getFileData());
    }

}