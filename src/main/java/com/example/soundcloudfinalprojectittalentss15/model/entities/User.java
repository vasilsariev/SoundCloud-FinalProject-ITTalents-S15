package com.example.soundcloudfinalprojectittalentss15.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String displayName;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private int age;
    @Column
    private String gender;
    @Column
    private String verificationCode;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String bio;
    @Column
    private LocalDateTime createdAt;
    @Column
    private LocalDateTime lastLogin;
    @Column
    private String profilePictureUrl;
    @Column
    private String backgroundPictureUrl;
    @Column
    private Boolean isVerified;
    @Column
    private int attempts;
    @Column
    private boolean isBlocked;
    @Column
    private String resetCode;
    @OneToMany(mappedBy = "user")
    private Set<Comment> comments;
    @ManyToMany
    @JoinTable(
            name = "users_like_tracks",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "track_id"))
    private Set<Track> likedTracks;

    @ManyToMany
    @JoinTable(name = "followers",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "followed_id"))
    private Set<User> followedUsers = new HashSet<>();
    @ManyToMany(mappedBy = "followedUsers")
    private Set<User> followers = new HashSet<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private Set<Playlist> playlists = new HashSet<>();
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private Set<Track> tracks;

    @ManyToMany
    @JoinTable(
            name = "users_like_playlists",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "playlist_id"))
    private Set<Playlist> likedPlaylists;

    @ManyToMany(mappedBy = "likedPlaylists")
    private Set<User> likedBy;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
