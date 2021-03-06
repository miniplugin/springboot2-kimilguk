package com.edu.springboot2.service.simple_users;

import com.edu.springboot2.domain.simple_users.SimpleUsers;
import com.edu.springboot2.domain.simple_users.SimpleUsersRepository;
import com.edu.springboot2.web.dto.SimpleUsersDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 이 클래스는 회원정보를 받아서 DAO 레포지토리를 호출 하는 기능
 */
@RequiredArgsConstructor
@Service
public class SimpleUsersService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private final SimpleUsersRepository simpleUsersRepository;

    //로그인 아이디로 회원정보 불러오기(아래)
    @Transactional
    public SimpleUsersDto findByName(String username){
        SimpleUsers entity = simpleUsersRepository.findByName(username);
        return new SimpleUsersDto(entity);
    }
    @Transactional
    public void delete (Long id){
        SimpleUsers posts = simpleUsersRepository.findById(id).orElseThrow(()->new
                IllegalArgumentException("해당 회원이 없습니다. id="+ id));
        simpleUsersRepository.delete(posts);
    }
    @Transactional
    public Long update(Long id, SimpleUsersDto requestDto){
        SimpleUsers simpleUsers = simpleUsersRepository.findById(id).orElseThrow(() -> new
                IllegalArgumentException("해당 회원이 없습니다. id="+ id));
        simpleUsers.update(requestDto.getUsername(),requestDto.getPassword(),requestDto.getRole(),requestDto.getEnabled(), requestDto.getEmail());
        return id;
    }
    @Transactional
    public SimpleUsersDto findById(Long id){
        SimpleUsers entity = simpleUsersRepository.findById(id).orElseThrow(() -> new
                IllegalArgumentException("헤당 회원이 없습니다. id="+id));
        return new SimpleUsersDto(entity);
    }
    @Transactional
    public Long save(SimpleUsersDto requestDto){
        return simpleUsersRepository.save(requestDto.toEntity()).getId();
    }
    @Transactional(readOnly = true)
    public List<SimpleUsersDto> findAllDesc(){
        return simpleUsersRepository.findAllDesc().stream()
                .map(SimpleUsersDto::new)
                .collect(Collectors.toList());
    }
}
