package com.example.soundcloudfinalprojectittalentss15.services;

import com.example.soundcloudfinalprojectittalentss15.model.DTOs.UserWithoutPasswordDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.trackDTOs.TrackDTO;
import com.example.soundcloudfinalprojectittalentss15.model.entities.Track;
import com.example.soundcloudfinalprojectittalentss15.model.entities.User;
import com.example.soundcloudfinalprojectittalentss15.model.exceptions.BadRequestException;
import com.example.soundcloudfinalprojectittalentss15.model.exceptions.NotFoundException;
import com.example.soundcloudfinalprojectittalentss15.model.exceptions.UnauthorizedException;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.util.Optional;
import java.util.UUID;

@Service
public class MediaService extends AbstractService{


    @SneakyThrows
    public UserWithoutPasswordDTO uploadProfilePicture(MultipartFile file, int userId) {
        String name = createFileName(file);
        File dir = new File("pictures");
        if(!dir.exists()) {
            dir.mkdirs();
        }
        File f = new File(dir, name);
        Files.copy(file.getInputStream(), f.toPath());
        String url = dir.getName() + File.separator + f.getName();
        User u = getUserById(userId);
        if (u.getProfilePictureUrl() != null) {
            // Delete the current profile picture
            File currentProfilePicture = new File(u.getProfilePictureUrl());
            if (currentProfilePicture.exists()) {
                currentProfilePicture.delete();
            }
        }

        u.setProfilePictureUrl(url);
        userRepository.save(u);
        return mapper.map(u, UserWithoutPasswordDTO.class);
    }



    @SneakyThrows
    public UserWithoutPasswordDTO uploadBackgroundPicture(MultipartFile file, int userId) {
        String name = createFileName(file);
        File dir = new File("pictures");
        if(!dir.exists()) {
            dir.mkdirs();
        }
        File f = new File(dir, name);
        Files.copy(file.getInputStream(), f.toPath());
        String url = dir.getName() + File.separator + f.getName();
        User u = getUserById(userId);
        if (u.getBackgroundPictureUrl() != null) {
            // Delete the current profile picture
            File currentProfileBackground = new File(u.getBackgroundPictureUrl());
            if (currentProfileBackground.exists()) {
                currentProfileBackground.delete();
            }
        }

        u.setBackgroundPictureUrl(url);
        userRepository.save(u);
        return mapper.map(u, UserWithoutPasswordDTO.class);
    }

    public File download(String fileName) {
        File dir = new File("pictures");

        File f = new File(dir, fileName);
        System.out.println(f.getName());
        if(f.exists()){
            return f;
        }
        throw new NotFoundException("File not found");
    }

    public TrackDTO uploadTrackCoverPicture(MultipartFile file, int trackId, int loggedId) {
        Track track = getTrackById(trackId);
        if(track.getOwner().getId() != loggedId) {
            throw new UnauthorizedException("Not authorized action! ");
        }
        String name = createFileName(file);
        String url = createFile(file, name);
        track.setCoverPictureUrl(url);
        trackRepository.save(track);
        return mapper.map(track, TrackDTO.class);
    }



    protected String createFileName(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        return UUID.randomUUID() + "." + extension;
    }

    @SneakyThrows
    protected String createFile(MultipartFile file, String name) {
        File dir = new File("pictures");
        if(!dir.exists()) {
            dir.mkdirs();
        }
        File f = new File(dir, name);
        Files.copy(file.getInputStream() , f.toPath());
        String url = dir.getName() + File.separator + f.getName();
        return url;

    }



}
