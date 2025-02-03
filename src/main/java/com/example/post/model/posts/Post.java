package com.example.post.model.posts;

import java.time.LocalDateTime;

import com.example.post.model.users.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Post {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;			// 게시글 ID
	private String title;		// 제목
	
	@Lob
	private String content;		// 내용
	
	@ManyToOne(fetch = FetchType.LAZY)	// 디폴트 즉시 로딩 (지연 로딩으로 하는게 좋음)
	@JoinColumn(name = "user_id")
	private User user;			// 작성자
	private int views;			// 조회수
	private LocalDateTime createTime;	// 작성일
	
	// 조회수 증가
	public void incrementViews() {
		this.views++;
	}
	
	
}
