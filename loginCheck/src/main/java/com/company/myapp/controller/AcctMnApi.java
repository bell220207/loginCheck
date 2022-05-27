package com.company.myapp.controller;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.ModelAndView;
import com.company.myapp.command.AJaxResVO;
import com.company.myapp.command.ActMnVO;
import com.company.myapp.service.AcctMnApiService;

@Controller
public class AcctMnApi{
	
	private static final Logger logger = LoggerFactory.getLogger(AcctMnApi.class);
	
	@Autowired
	AcctMnApiService actMnApiService;
	
	// 회원가입 화면 이동
	@RequestMapping("user/SignUpView")
	public void SignUpView() {
	}
	
	// 회원가입 기능
	@RequestMapping(value="user/SignUp", method=RequestMethod.POST)
	public String SignUP(ActMnVO vo) {
		boolean result=true;
		
		System.out.println(vo.toString());
		
		try {
			result = actMnApiService.SignUp(vo);
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(result) {
			System.out.println("성공");
			return "user/loginView";
		}else {
			System.out.println("실패");
			return "redirect:/user/SignUp";
		}
	}
	
	// 로그인 화면
	@RequestMapping("/user/loginView")
	public void LoginView() {
	}
	
	// 로그아웃 기능
	@RequestMapping("user/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		System.out.println("로그아웃 됨");
		return "redirect:/";
	}
	
	// 로그인 실패 횟수 제한
	@ResponseBody
	@RequestMapping(value="/loginCheckApi", method=RequestMethod.POST)
	public AJaxResVO loginCheckApi(HttpServletRequest request, HttpServletResponse response ){
		response.setHeader("Access-Control-Allow-Origin", "*");
		ActMnVO actMnVo = new ActMnVO();
		AJaxResVO jRes = new AJaxResVO();
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		String id="";
		String pwd="";
		String name="";
		Integer count=0;
		String lck="";
		String msg = "";
		
		try {
			String input_id = request.getParameter("id");
			String input_pwd = request.getParameter("pwd");
			System.out.println("input_pwd: "+input_pwd);
			
			logger.info("입력한 계정 ID: "+ input_id +" PWD: "+input_pwd);
			
			actMnVo.setUsr_id(input_id); // 아이디만 set
			ActMnVO IdCheck = actMnApiService.login_Check(actMnVo);
			
			if(IdCheck==null) { // 아이디 없음
				logger.info("존재하지 않는 아이디입니다.");
				msg = "존재하지 않는 아이디입니다";
				jRes.setSuccess(AJaxResVO.SUCCESS_N);
				
			}else { // 아이디 있음, 비번 조회
				// idcheck 결과
				String idCheck_result = IdCheck.toString();
				System.out.println(idCheck_result); // 확인용
				
				/*
				 * 
				String testpw= IdCheck.getPwd_no_enc_cntn();
				System.out.println("testpw1: "+testpw);
				
				testpw = URLDecoder.decode(testpw, "UTF-8");
				System.out.println("testpw2: "+testpw);
				
				testpw = URLDecoder.decode(testpw, "UTF-8");
				System.out.println("testpw3: "+testpw);
				 */
				
				// 들어온 값을 SET 해주고 비교해야 하니까..
				actMnVo.setPwd_no_enc_cntn(input_pwd); // vo에 넣었어
				
				ActMnVO PwCheck = actMnApiService.login_Check(actMnVo);
				
				if(PwCheck!= null) { // 비번 있음
					id = PwCheck.getUsr_id();
					pwd = PwCheck.getPwd_no_enc_cntn();
					name = PwCheck.getUsr_nm();
					count = PwCheck.getUsr_count();
					lck = PwCheck.getUsr_lck();
					System.out.println("pwd: "+pwd);
					
					if(lck!=null) {
						if(lck.equals("Y")) { // 비번 맞음, 잠금계정
							logger.info("패스워드 일치, 잠긴 계정입니다.");
							msg = "잠긴 계정입니다. \n관리자에게 문의하세요. (000-0000-0000)";
							jRes.setSuccess(AJaxResVO.SUCCESS_N);
						}else if(lck.equals("N")) { // 비번 맞음, 로그인 성공
							logger.info("로그인 성공.");
							actMnApiService.ClearCnt(PwCheck.getUsr_id()); // cnt 초기화
							count=0;
							msg="로그인 성공";
							jRes.setSuccess(AJaxResVO.SUCCESS_Y);
						}
					}
				}else{ // 비번 틀림
					actMnApiService.failCntUpdate(actMnVo); // count 증가
					ActMnVO cntResult = actMnApiService.failCntGet(actMnVo); // count + lck 조회
					count = cntResult.getUsr_count(); // count 조회
					
					if(cntResult.getUsr_lck().equals("N")==true) {
						if(1<=count&&count<5) { // 잠금계정 X
							logger.info("로그인 실패. 로그인 실패 횟수 증가."); // 잠금계정 아님
							msg = "로그인 "+Integer.toString(count)+"회 실패. 5회 실패 시 계정이 잠깁니다.";
							jRes.setSuccess(AJaxResVO.SUCCESS_N);
							
						}else if(count>=5) { // 잠금계정 O
							logger.info("로그인 실패 한도 초과, 계정 잠금 처리."); // 잠금계정 아님
							msg="로그인 실패 한도를 초과하여 계정이 잠금 처리 되었습니다. \n관리자에게 문의하세요. (000-0000-0000)";
							actMnApiService.LckUpdate_Y(actMnVo);
							jRes.setSuccess(AJaxResVO.SUCCESS_N);
						}
					}else if(cntResult.getUsr_lck().equals("Y")==true){
						logger.info("패스워드 불일치, 잠긴 계정임");
						msg = "잠긴 계정입니다. \n관리자에게 문의하세요. (000-0000-0000)";
						jRes.setSuccess(AJaxResVO.SUCCESS_N);
					}
				} // 비번틀림 끝
			} // 아이디 있고 비번조회
			map.put("id", id);
			map.put("pwd", pwd);
			map.put("name", name);
			map.put("count", count);
			map.put("lck", lck);
			map.put("msg", msg);
			
			jRes.addAttribute("result", map);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.toString());
		}
		return jRes;
	}
	
	// 관리자 화면 이동
	@RequestMapping("admin/MngAccount")
	public void ClearAccountView(Model model) {
		ArrayList<ActMnVO> userList = actMnApiService.getList();
		model.addAttribute("results", userList);
	}
	
	// 잠금해제 API
	@ResponseBody
	@RequestMapping(value="/MngAccount", method=RequestMethod.POST)
	public ActMnVO unlockAccount(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		ActMnVO vo = new ActMnVO();
		String id = request.getParameter("id");
		vo.setUsr_id(id);
		ActMnVO par = actMnApiService.login_Check(vo); // lck 조회
		int MngAccnt = actMnApiService.MngAccount(par); // 계정상태 변경
		ActMnVO result = actMnApiService.login_Check(par); // lck 반환
		
		return result;
	}
	
	
}
