package Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.MyUtil;
import VO.BoardVO;
import dao.BoardDao;

public class BoardServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
		try {
			process(req, resp);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
		try {
			process(req, resp);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void process(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException, ClassNotFoundException, SQLException
	{
		req.setCharacterEncoding("UTF-8");
		
		BoardDao dao = new BoardDao();
		MyUtil myUtil = new MyUtil();
		
		String cp = req.getContextPath(); //주소줄의 앞 부분 문자열 얻기, 절대주소 지정시 필요
		String uri = req.getRequestURI(); //주소줄의 뒷 부분, 하나의 서블릿으로 여러개의 서블릿 주소 연결시 필요
		
		String url;
		
		//주소줄의 서블릿 이름 분석후 url 이동
		if(uri.indexOf("list.do")!= -1){
			//페이징 처리부분
			
			//페이징번호 받기
			String pageNum = req.getParameter("pageNum");
			
			int current_page = 1;
			if(pageNum != null){
				current_page = Integer.parseInt(pageNum);
			}
			
			//전체 데이터수확인
			int total_dataCount = 0;
			total_dataCount = dao.getDataCount();
			
			//전체 페이지수 확인
			int numPerPage = 5;
			int total_page = myUtil.getPageCount(numPerPage, total_dataCount);
			if(current_page > total_page){
				current_page = total_page;
			}
			
			//데이터베이스에서 가져올 데이터의 시작과 끝 계산
			int start = (current_page - 1) * numPerPage + 1;
			int end = current_page * numPerPage;
			
			//특정 페이지 자료 읽어오는 select 쿼리 메소드 호출
			List<BoardVO> list = dao.getListData(start,end);
			
			//페이지 번호 출력을 위한 준비
			String list_url = "list.do";
			String pageIndexList = myUtil.pageIndexList(current_page, total_page, list_url);
			
			//데이터베이스의 자료를 출력 페이지로 넘기기 위한 준비 -> setAttribute();
			req.setAttribute("list",list);
			req.setAttribute("pageIndexList",pageIndexList);
			//자유게시판 리스트 페이지 이동
			url="/BoardList.jsp";
			
			//페이지 이동 전용 메소드 호출
			forward(req, resp, url);
			
		}else if(uri.indexOf("insert.do")!= -1){
			
			BoardVO vo = new BoardVO();
			
			//새글 번호 얻기
			vo.setNum(dao.getMaxNum()+1);
			
			//제목, 작성자, 내용, 패스워드 수신
			
			vo.setSubject(req.getParameter("subject"));
			vo.setName(req.getParameter("name"));
			vo.setContent(req.getParameter("content"));
			vo.setPwd(req.getParameter("pwd"));
			
			//ip 주소얻기
			vo.setIpAddr(req.getRemoteAddr());
			
			//INSERT
			dao.insertDate(vo);
			
			//리스트 페이지로 이동
			resp.sendRedirect("list.do");
			
		}else if(uri.indexOf("delete.do")!= -1){
			String num = req.getParameter("num");
			
			//패스워드 입력 받는 화면으로 이동 -> 포워딩
			//글번호 재전송 -> setAttribute()
			req.setAttribute("num",num);
			url = "BoardDelete.jsp";
			forward(req,resp,url);
		}else if(uri.indexOf("delete_ok.do")!= -1){
			
			//글번호, 패스워드 수신
			int num = Integer.parseInt(req.getParameter("num"));
			String pwd = req.getParameter("pwd");
			
			//delete 쿼리 시행
			dao.deleteData(num,pwd);
			
			//리스트 페이지 이동
			resp.sendRedirect("list.do");
		}else if(uri.indexOf("update.do")!= -1){
			
			//글번호 수신
			int num = Integer.parseInt(req.getParameter("num"));
			
			//글번호, 패스워드 입력 받는 화면으로 이동
			//글번호 재전송
			BoardVO vo = dao.getReadData(num);
			req.setAttribute("vo",vo);
			url = "BoardUpdate.jsp";
			forward(req, resp,url);
		}else if(uri.indexOf("update_ok.do")!= -1){
			
			BoardVO vo = new BoardVO();
			//수정 내용 및 패스워드 입력후 수정 버튼 클릭 - > 수정항목 전송
			//수정 버튼 클릭시 URL 지정
			//수정항목 수신
			
			vo.setName(req.getParameter("name"));
			vo.setSubject(req.getParameter("subject"));
			vo.setContent(req.getParameter("content").replace("\n","<br>" ));
			vo.setNum(Integer.parseInt(req.getParameter("num")));
			vo.setPwd(req.getParameter("pwd"));
			vo.setIpAddr(req.getRemoteAddr());
			
			//update쿼리 호출
			dao.updateData(vo);
			
			//리스트 페이지 이동
			resp.sendRedirect("list.do");
		}else if(uri.indexOf("adminLogin.do")!= -1){
			url = "BoardLogin.jsp";
			forward(req, resp, url);
		}else if(uri.indexOf("adminLogin_ok.do")!= -1){
			
			//아이디, 패스워드 수신
			String uid = req.getParameter("uid");
			String upw = req.getParameter("upw");
			
			//아이디, 패스 검사
			if(uid.equals("admin")&& upw.equals("1234")){
				//맞으면 세션생성,이동
				
				HttpSession session = req.getSession(true);
				session.setAttribute("login","true");
				
				resp.sendRedirect("list.do");
			}else{
				req.setAttribute("errMsg","안맞음");
				url ="BoardLogin.jsp";
				forward(req,resp,url);
			}
		}else if(uri.indexOf("adminLogout.do")!= -1){
			HttpSession session = req.getSession();
			session.invalidate();
			resp.sendRedirect("list.do");
		}else if(uri.indexOf("adminDelete.do")!= -1){
			req.setAttribute("errMsg","틀렷다");
			int num = Integer.parseInt(req.getParameter("num"));
			dao.deleteData(num);
			resp.sendRedirect("list.do");
		}
	}
	
	protected void forward(HttpServletRequest req, HttpServletResponse resp, String url)throws ServletException, IOException{
		
		RequestDispatcher id =req.getRequestDispatcher(url);
		id.forward(req, resp);
	}
}
