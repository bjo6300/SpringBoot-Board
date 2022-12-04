package boardexample.board.domain.member.service;

import boardexample.board.domain.member.Member;
import boardexample.board.domain.member.dto.MemberInfoDto;
import boardexample.board.domain.member.dto.MemberSignUpDto;
import boardexample.board.domain.member.dto.MemberUpdateDto;
import boardexample.board.domain.member.repository.MemberRepository;
import boardexample.board.global.util.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /*
    * 회원가입을 진행하는 메서드입니다.
    * 회원가입 시 컨트롤러 단에서 엔티티로 변환하여 받아오는 것이 아니라, 서비스 단에서 DTO를 엔티티로 변환하였습니다.
    * 변환 후 USER라는 권한을 설정하였고, 이후 중복된 아이디가 있는지 체크합니다.
    * 없다면 회원가입을 진행합니다.
    * */

    @Override
    public void signUp(MemberSignUpDto memberSignUpDto) throws Exception {
        Member member = memberSignUpDto.toEntity();
        member.addUserAuthority();
        member.encodePassword(passwordEncoder);

        if(memberRepository.findByUsername(memberSignUpDto.username()).isPresent()){
            throw new Exception("이미 존재하는 아이디입니다.");
        }

        memberRepository.save(member);
    }

    /*
    * 회원정보를 수정합니다.
    * MemberUpdateDto는 Optional 필드들을 가지고 있으며,
    * ifPresent를 통해 필드가 존재하는 경우에만 업데이트를 진행하도록 작성하였습니다.
    * */

    @Override
    public void update(MemberUpdateDto memberUpdateDto) throws Exception {
        Member member = memberRepository.findByUsername(SecurityUtil.getLoginUsername()).orElseThrow(() -> new Exception("회원이 존재하지 않습니다"));

        memberUpdateDto.age().ifPresent(member::updateAge);
        memberUpdateDto.name().ifPresent(member::updateName);
        memberUpdateDto.nickName().ifPresent(member::updateNickName);
    }

    /*
    * 비밀번호를 변경하는 메서드입니다.
    * 비밀번호는 다른 회원정보들과 다르게 무조건 따로 업데이트 해야 하며,
    * 비밀번호 변경시에는 현재 비밀번호를 입력받아 보안을 강화합니다.
    * Member 클래스에 matchPassword()라는 메서드를 만들어 비밀번호가 일치하는지 확인하는 메서드를 생성하였고,
    * 일치한다면, 변경하고자 하는 비밀번호 (toBePassword)로 변경합니다
    * */
    @Override
    public void updatePassword(String checkPassword, String toBePassword) throws Exception {
        Member member = memberRepository.findByUsername(SecurityUtil.getLoginUsername()).orElseThrow(() -> new Exception("회원이 존재하지 않습니다"));

        if(!member.matchPassword(passwordEncoder, checkPassword) ) {
            throw new Exception("비밀번호가 일치하지 않습니다.");
        }

        member.updatePassword(passwordEncoder, toBePassword);
    }

    /*
    * 회원탈퇴를 진행하는 메서드입니다.
    * 비밀번호를 재입력받아 비밀번호가 일치해야만 회원탈퇴를 진행합니다.
    * */
    @Override
    public void withdraw(String checkPassword) throws Exception {
        Member member = memberRepository.findByUsername(SecurityUtil.getLoginUsername()).orElseThrow(() -> new Exception("회원이 존재하지 않습니다"));

        if(!member.matchPassword(passwordEncoder, checkPassword) ) {
            throw new Exception("비밀번호가 일치하지 않습니다.");
        }

        memberRepository.delete(member);
    }



    /*
    * id를 받아와서 해당 회원의 정보를 조회하는 메서드입니다.
    * MemberInfoDto의 형태로 감싸서 반환하며 이는 이후에 비공개 계정과 공개 계정을 나누어 비공개 계정일 경우
    * 정보조회를 할 수 없도록 만들고 싶습니다.
    * */
    @Override
    public MemberInfoDto getInfo(Long id) throws Exception {
        Member findMember = memberRepository.findById(id).orElseThrow(() -> new Exception("회원이 없습니다"));
        return new MemberInfoDto(findMember);
    }

    /*
    * 나의 정보를 가져오는 메서드입니다.
    * 현재 나의 정보는 로그인 한 경우 SecurityContextHolder에 들어있기 때문에
    * 따로 입력받지 않아도 인증만 되어있다면 정보 조회가 가능합니다.
    * */
    @Override
    public MemberInfoDto getMyInfo() throws Exception {
        Member findMember = memberRepository.findByUsername(SecurityUtil.getLoginUsername()).orElseThrow(() -> new Exception("회원이 없습니다"));
        return new MemberInfoDto(findMember);
    }
}

