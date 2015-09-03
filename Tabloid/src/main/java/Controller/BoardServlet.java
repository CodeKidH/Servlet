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
		
		String cp = req.getContextPath(); //�ּ����� �� �κ� ���ڿ� ���, �����ּ� ������ �ʿ�
		String uri = req.getRequestURI(); //�ּ����� �� �κ�, �ϳ��� �������� �������� ���� �ּ� ����� �ʿ�
		
		String url;
		
		//�ּ����� ���� �̸� �м��� url �̵�
		if(uri.indexOf("list.do")!= -1){
			//����¡ ó���κ�
			
			//����¡��ȣ �ޱ�
			String pageNum = req.getParameter("pageNum");
			
			int current_page = 1;
			if(pageNum != null){
				current_page = Integer.parseInt(pageNum);
			}
			
			//��ü �����ͼ�Ȯ��
			int total_dataCount = 0;
			total_dataCount = dao.getDataCount();
			
			//��ü �������� Ȯ��
			int numPerPage = 5;
			int total_page = myUtil.getPageCount(numPerPage, total_dataCount);
			if(current_page > total_page){
				current_page = total_page;
			}
			
			//�����ͺ��̽����� ������ �������� ���۰� �� ���
			int start = (current_page - 1) * numPerPage + 1;
			int end = current_page * numPerPage;
			
			//Ư�� ������ �ڷ� �о���� select ���� �޼ҵ� ȣ��
			List<BoardVO> list = dao.getListData(start,end);
			
			//������ ��ȣ ����� ���� �غ�
			String list_url = "list.do";
			String pageIndexList = myUtil.pageIndexList(current_page, total_page, list_url);
			
			//�����ͺ��̽��� �ڷḦ ��� �������� �ѱ�� ���� �غ� -> setAttribute();
			req.setAttribute("list",list);
			req.setAttribute("pageIndexList",pageIndexList);
			//�����Խ��� ����Ʈ ������ �̵�
			url="/BoardList.jsp";
			
			//������ �̵� ���� �޼ҵ� ȣ��
			forward(req, resp, url);
			
		}else if(uri.indexOf("insert.do")!= -1){
			
			BoardVO vo = new BoardVO();
			
			//���� ��ȣ ���
			vo.setNum(dao.getMaxNum()+1);
			
			//����, �ۼ���, ����, �н����� ����
			
			vo.setSubject(req.getParameter("subject"));
			vo.setName(req.getParameter("name"));
			vo.setContent(req.getParameter("content"));
			vo.setPwd(req.getParameter("pwd"));
			
			//ip �ּҾ��
			vo.setIpAddr(req.getRemoteAddr());
			
			//INSERT
			dao.insertDate(vo);
			
			//����Ʈ �������� �̵�
			resp.sendRedirect("list.do");
			
		}else if(uri.indexOf("delete.do")!= -1){
			String num = req.getParameter("num");
			
			//�н����� �Է� �޴� ȭ������ �̵� -> ������
			//�۹�ȣ ������ -> setAttribute()
			req.setAttribute("num",num);
			url = "BoardDelete.jsp";
			forward(req,resp,url);
		}else if(uri.indexOf("delete_ok.do")!= -1){
			
			//�۹�ȣ, �н����� ����
			int num = Integer.parseInt(req.getParameter("num"));
			String pwd = req.getParameter("pwd");
			
			//delete ���� ����
			dao.deleteData(num,pwd);
			
			//����Ʈ ������ �̵�
			resp.sendRedirect("list.do");
		}else if(uri.indexOf("update.do")!= -1){
			
			//�۹�ȣ ����
			int num = Integer.parseInt(req.getParameter("num"));
			
			//�۹�ȣ, �н����� �Է� �޴� ȭ������ �̵�
			//�۹�ȣ ������
			BoardVO vo = dao.getReadData(num);
			req.setAttribute("vo",vo);
			url = "BoardUpdate.jsp";
			forward(req, resp,url);
		}else if(uri.indexOf("update_ok.do")!= -1){
			
			BoardVO vo = new BoardVO();
			//���� ���� �� �н����� �Է��� ���� ��ư Ŭ�� - > �����׸� ����
			//���� ��ư Ŭ���� URL ����
			//�����׸� ����
			
			vo.setName(req.getParameter("name"));
			vo.setSubject(req.getParameter("subject"));
			vo.setContent(req.getParameter("content").replace("\n","<br>" ));
			vo.setNum(Integer.parseInt(req.getParameter("num")));
			vo.setPwd(req.getParameter("pwd"));
			vo.setIpAddr(req.getRemoteAddr());
			
			//update���� ȣ��
			dao.updateData(vo);
			
			//����Ʈ ������ �̵�
			resp.sendRedirect("list.do");
		}else if(uri.indexOf("adminLogin.do")!= -1){
			url = "BoardLogin.jsp";
			forward(req, resp, url);
		}else if(uri.indexOf("adminLogin_ok.do")!= -1){
			
			//���̵�, �н����� ����
			String uid = req.getParameter("uid");
			String upw = req.getParameter("upw");
			
			//���̵�, �н� �˻�
			if(uid.equals("admin")&& upw.equals("1234")){
				//������ ���ǻ���,�̵�
				
				HttpSession session = req.getSession(true);
				session.setAttribute("login","true");
				
				resp.sendRedirect("list.do");
			}else{
				req.setAttribute("errMsg","�ȸ���");
				url ="BoardLogin.jsp";
				forward(req,resp,url);
			}
		}else if(uri.indexOf("adminLogout.do")!= -1){
			HttpSession session = req.getSession();
			session.invalidate();
			resp.sendRedirect("list.do");
		}else if(uri.indexOf("adminDelete.do")!= -1){
			req.setAttribute("errMsg","Ʋ�Ǵ�");
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
