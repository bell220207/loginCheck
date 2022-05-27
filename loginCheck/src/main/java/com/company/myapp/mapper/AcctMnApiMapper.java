package com.company.myapp.mapper;
import java.util.ArrayList;
import java.util.List;

import com.company.myapp.command.ActMnVO;

public interface AcctMnApiMapper {
	
	// 회원가입
	public boolean SignUp(ActMnVO vo);
	
	// 로그인 체크
	public ActMnVO login_Check(ActMnVO vo);
	
	// count 조회하기
	public ActMnVO failCntGet(ActMnVO vo);

	// count 늘리기
	public int failCntUpdate(ActMnVO vo);
	
	// lck Y로 늘리기
	public int LckUpdate_Y(ActMnVO vo);

	// DB 데이터 뿌리기
	public ArrayList<ActMnVO> getList();

	// count 0으로 초기화
	public int ClearCnt(String id);

	// 계정 잠금 및 잠금 해제
	public int MngAccount(ActMnVO vo);
}
