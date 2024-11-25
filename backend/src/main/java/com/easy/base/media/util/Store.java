package com.easy.base.media.util;


import com.easy.base.media.dto.FileDetailsDto;
import com.easy.base.media.dto.FolderDto;
import com.easy.base.model.MediaFile;
import com.easy.base.model.MediaFolder;
import com.easy.base.service.MediaFileService;
import com.easy.base.service.MediaFolderService;
import org.apache.commons.io.FileUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Store {
    private static final String rootDir="upload";

    public static boolean saveFile(InputStream inputStream,String filePath,String fileName, String mimeType){
        try{
        File root = new File(rootDir+filePath);
        boolean result = root.mkdirs();
        Path file = Paths.get(rootDir+filePath, fileName);
        // Create and write the file
        FileOutputStream outputStream = new FileOutputStream(file.toFile());
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public static String createFolder(String folderName, String parentId, MediaFolderService mediaFolderService) {
        return addFolder(folderName,parentId,mediaFolderService).getFolderId();
    }
    public static MediaFolder addFolder(String folderName, String parentId, MediaFolderService mediaFolderService){
        return parentId == null || parentId.isBlank()?mediaFolderService.addFolder(folderName):mediaFolderService.addFolder(folderName,parentId);
    }

    public static Page<FolderDto> getFoldesInFolder(String folderId, Pageable pagable, MediaFolderService mediaFolderService) {
        Page<MediaFolder> mediaFolders = listOfFolders(folderId,pagable, mediaFolderService);
        return new PageImpl<>(mediaFolders.stream().map(folder->{
            return FolderDto.builder().folderId(folder.getFolderId()).folderName(folder.getFolderName()).build();
        }).collect(Collectors.toList()),pagable,mediaFolders.getTotalPages());
    }

    private static Page<MediaFolder> listOfFolders(String folderId, Pageable pageable, MediaFolderService mediaFolderService) {
        return folderId == null?mediaFolderService.findByparentId("", pageable):mediaFolderService.findByparentId(folderId, pageable);
    }

    public static void deleteFolderContent(String folderId, MediaFolderService mediaFolderService) {
        mediaFolderService.deleteFolders(folderId);
    }
    public static void deleteFromServer(String path) throws IOException {
        File rootDerectory = new File(rootDir);
        File child = new File(rootDir+path);
        System.out.println(FileUtils.directoryContains(rootDerectory,child));
        if(FileUtils.directoryContains(rootDerectory,child)){
            FileUtils.deleteQuietly(child);
        }
    }

    public static String createFile(List<MultipartFile> files,MediaFileService mediaFileService,String folderId) {
        return files.isEmpty()?"empty list": storeAndSaveFiles(files,mediaFileService,folderId);
    }

    private static String storeAndSaveFiles(List<MultipartFile> files, MediaFileService mediaFileService,String folderId){
        files.stream().forEach( file->{
            InputStream is = null;
            try {
                is = file.getInputStream();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            MediaFile mediaFile = folderId == null || folderId.isBlank()?mediaFileService.addFile(file.getOriginalFilename(), file.getContentType(),is):mediaFileService.addFile(file.getOriginalFilename(),file.getContentType(),folderId,is);
         });
        return "Success";
    }

    public static FileDetailsDto fileDetails(String fileId, MediaFileService mediaFileService) {
        MediaFile mediaFile =  mediaFileService.findById(fileId);
        return FileDetailsDto.builder().fileName(mediaFile.getFileName()).createDate(mediaFile.getCreateDate()).url("media"+mediaFile.getFilePath()+mediaFile.getFileName()).build();
    }

    public static Page<FileDetailsDto> filesDetails(String folderId, Pageable pageable, MediaFileService mediaFileService) {
        Page<MediaFile> mediaFileList =  mediaFileService.findByParentId(folderId,pageable);
        return new PageImpl<>(mediaFileList.stream().map(mediaFile -> {
            return FileDetailsDto.builder().fileName(mediaFile.getFileName()).createDate(mediaFile.getCreateDate()).url("media"+mediaFile.getFilePath()+mediaFile.getFileName()).build();
        }).collect(Collectors.toList()),pageable,mediaFileList.getTotalPages());
    }

    public static void deleteFile(String fileId, MediaFileService mediaFileService) throws IOException {
        String path = mediaFileService.findById(fileId).getFilePath()+mediaFileService.findById(fileId).getFileName();
        deleteFromServer(path);
        mediaFileService.delete(fileId);
    }
}