package com.company.myapp.service;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.myapp.command.ActMnVO;
import com.company.myapp.mapper.AcctMnApiMapper;

@Service
public class AcctMnApiServiceImpl implements AcctMnApiService {

	@Autowired
	AcctMnApiMapper acctMnApiMapper;
	
	@Override
	public boolean SignUp(ActMnVO vo) {
		return acctMnApiMapper.SignUp(vo);
	}

	@Override
	public ActMnVO login_Check(ActMnVO vo) {
		return acctMnApiMapper.login_Check(vo);
	}

	@Override
	public int failCntUpdate(ActMnVO vo) {
		return acctMnApiMapper.failCntUpdate(vo);
	}

	@Override
	public ActMnVO failCntGet(ActMnVO vo) {
		return acctMnApiMapper.failCntGet(vo);
	}

	@Override
	public int LckUpdate_Y(ActMnVO vo) {
		return acctMnApiMapper.LckUpdate_Y(vo);
	}
	
	@Override
	public ArrayList<ActMnVO> getList() {
		return acctMnApiMapper.getList();
	}
	@Override
	public int ClearCnt(String id) {
		return acctMnApiMapper.ClearCnt(id);
	}

	@Override
	public int MngAccount(ActMnVO vo) {
		return acctMnApiMapper.MngAccount(vo);
	}

}
