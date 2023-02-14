package com.hcc.entities;

import javax.persistence.*;

@Entity
@Table(name = "assignments")
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "current_status")
    private String status;
    @Column(name = "assignment_number")
    private int number;
    @Column(name = "github_url")
    private String githubUrl;
    @Column(name = "branch")
    private String branch;
    @Column(name = "review_video_url")
    private String reviewVideoUrl;
    @ManyToOne
    private User user;
//    @Column(name = "code_reviewer")
//    private User codeReviewer;

    public Assignment(String status, int number, String githubUrl,String branch, String reviewVideoUrl, User user) { //User codeReviewer
        this.status = status;
        this.number = number; //TODO: need to make this auto increment each time a new assignment is created
        this.githubUrl = githubUrl;
        this.branch = branch;
        this.reviewVideoUrl = reviewVideoUrl;
        this.user = user;
//        this.codeReviewer = codeReviewer;
    }

    public Assignment() {
    }

    public Long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public int getNumber() {
        return number;
    }

    public String getGithubUrl() {
        return githubUrl;
    }

    public String getBranch() {
        return branch;
    }

    public String getReviewVideoUrl() {
        return reviewVideoUrl;
    }

    public User getUser() {
        return user;
    }

//    public User getCodeReviewer() {
//        return codeReviewer;
//    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setGithubUrl(String githubUrl) {
        this.githubUrl = githubUrl;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setReviewVideoUrl(String reviewVideoUrl) {
        this.reviewVideoUrl = reviewVideoUrl;
    }

    public void setUser(User user) {
        this.user = user;
    }

//    public void setCodeReviewer(User codeReviewer) {
//        this.codeReviewer = codeReviewer;
//    }

    public void setStatus(String status) {
        this.status = status;
    }
}
