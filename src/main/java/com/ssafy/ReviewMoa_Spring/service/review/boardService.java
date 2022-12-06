package com.ssafy.ReviewMoa_Spring.service.review;
import com.ssafy.ReviewMoa_Spring.dto.review.Board;
import com.ssafy.ReviewMoa_Spring.dto.review.Content;
import com.ssafy.ReviewMoa_Spring.repository.review.boardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class boardService {

    @Autowired
    private boardRepository boardRepo;

    public void createPost(Board board) {
        //내용 목록의 f.k를 board로 설정. 썸네일과 type은 클라쪽에서 설정해서 오기.
        for(Content content:board.getContentList()){
            content.setBoard(board);
        }
        //등록 시간 설정하고
        board.setRegistTime(LocalDate.now());

        //내용은 orders 순 정렬
        Collections.sort(board.getContentList(), Comparator.comparingInt(Content::getOrders));


        //저장하기
        boardRepo.save(board);
    }

    public List<Board> getList() {
        return boardRepo.findAll();
    }

    public Board getOne(Long postId) {
        return boardRepo.getReferenceById(postId);
    }

    public void deleteOne(Long postId) {
        boardRepo.deleteById(postId);
    }

    public void updatePost(Board board) {
        //바꿀 수 있는 것- contentList, genre, movieTitle,postTitle,thumbnail
        Optional<Board> entity = boardRepo.findById(board.getPostId());
        // ifPresent는 컨슈머를 매개변수로 입력받아서 객체가 존재할 때만 실행하는 Optional의 메소드
        entity.ifPresent(t ->{

            // 내용이 널이 아니라면 엔티티의 객체를 바꿔준다.
            if(board.getContentList() != null) {
                t.setContentList(new ArrayList<>());// 기존 배열 비우고
//                for(Content content:board.getContentList()){
//                    content.setBoard(board);
//                }
                t.setContentList(board.getContentList());
            }
            if(board.getGenre() != null) {
                t.setGenre(board.getGenre());
            }
            if(board.getGenre() != null) {
                t.setGenre(board.getGenre());
            }
            if(board.getMovieTitle() != null){
                t.setMovieTitle(board.getMovieTitle());
            }
            if(board.getPostTitle()!=null){
                t.setPostTitle(board.getPostTitle());
            }
            if(board.getThumbnail()!=null){
                t.setThumbnail(board.getThumbnail());
            }
            // 이걸 실행하면 idx 때문에 update가 실행됨
            boardRepo.save(t);
        });
    }

}
