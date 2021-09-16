package com.edu.springboot2.service.posts;

import com.edu.springboot2.domain.posts.Posts;
import com.edu.springboot2.domain.posts.PostsRepository;
import com.edu.springboot2.web.dto.PostsDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 이 클래스는 게시물 정보를 받아서 DAO 레포지토리를 호출 하는 기능
 */
@RequiredArgsConstructor//이 어노테이션은 초기화 되지않은 final 객체에 대해 생성자를 생성해 줍니다.
@Service//이 어노테이션은 클래스를 스프링빈으로 사용가능하게 함.
public class PostsService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final PostsRepository postsRepository;

    @Transactional
    public void delete (Long id){
        Posts posts = postsRepository.findById(id).orElseThrow(()->new
                IllegalArgumentException("해당 게시글이 없습니다. id="+ id));
        postsRepository.delete(posts);
    }
    @Transactional
    public Long update(Long id, PostsDto requestDto){
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new
                IllegalArgumentException("해당 게시글이 없습니다. id="+ id));
        posts.update(requestDto.getTitle(), requestDto.getContent(), requestDto.getFileId());
        return id;
    }
    @Transactional
    public PostsDto findById(Long id){
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new
                IllegalArgumentException("헤당 게시글이 없습니다. id="+id));
        return new PostsDto(entity);
    }
    @Transactional
    public Long save(PostsDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }
    private static final int PAGE_POST_COUNT = 5; // 한 페이지에 존재하는 게시글 수
    @Transactional
    public Page<Posts> getPostsList(String keyword, Integer pageNum) {
        Page<Posts> page = postsRepository.findByTitleContaining(keyword, PageRequest.of(pageNum, PAGE_POST_COUNT, Sort.by(Sort.Direction.DESC, "id")));
        return page;
    }
    private static final int BLOCK_PAGE_NUM_COUNT = 4; // 블럭에 존재하는 페이지 번호 수
    public Integer[] getPageList(Long postsTotalCount, Integer totalLastPageNum, Integer curPageNum) {
        //페이지 번호 배열 반환값 선언
        Integer[] pageList = new Integer[BLOCK_PAGE_NUM_COUNT];
        logger.info(totalLastPageNum + " 비교 " + (curPageNum));
        //블럭 끝 번호 구하기
        Integer blockLastPageNum = BLOCK_PAGE_NUM_COUNT;
        logger.info("블럭 마지막 페이지1 " + blockLastPageNum);
        // 페이지 시작 번호 조정
        int startBlockNum = (int)Math.ceil((curPageNum) / blockLastPageNum);
        int startPageNum = startBlockNum * BLOCK_PAGE_NUM_COUNT;
        //int startPageNum = (int)Math.ceil((curPageNum-1) / totalLastPageNum)+1;
        logger.info("여기 startPageNum " + startPageNum);
        //제일 마지막 페이지 일때
        if (totalLastPageNum == (curPageNum + 1) ) {
            int blockLastPageNumTmp = (int) (postsTotalCount - (curPageNum * PAGE_POST_COUNT)) - 1;//제일 마지막 페이지 갯수 구하기
            if(blockLastPageNumTmp < BLOCK_PAGE_NUM_COUNT) {//마지막 개수가 BLOCK_PAGE_NUM_COUNT 개수보다 작을때
                blockLastPageNum = blockLastPageNumTmp + 1;
                logger.info("여기!1 " + blockLastPageNumTmp);
            }
        }
        //제일 마지막 페이지가 블럭번호 보다 작을때
        if (totalLastPageNum < BLOCK_PAGE_NUM_COUNT) {
            blockLastPageNum = totalLastPageNum;
        }
        // 페이지 번호 할당
        for (int val = startPageNum, idx = 0; idx < blockLastPageNum; val++, idx++) {
            pageList[idx] = val;
            logger.info("pageList[{}] = {} ", idx, pageList[idx]);//페이지 번호는 jsp 에서...
        }
        return pageList;
    }
}
