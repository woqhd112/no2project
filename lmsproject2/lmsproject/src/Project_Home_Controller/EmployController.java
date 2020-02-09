package Project_Home_Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Project_Home_Model.Home_Student_Dao;
import Project_LMS_Model.LMS_HireInfo_Dto;
import Project_Utils.Common_dao;
import Project_Utils.Paging;

@WebServlet("/Home/MyPage/employ.html")
public class EmployController extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		Home_Student_Dao dao=new Home_Student_Dao();
		Common_dao com=new Common_dao();
		
		//���̺��� �ο찳�� ���ϴ°�
		int totalCount=0;	//�ο찹��
		String tablename="hireinfo";	//���̺��̸�
		try {
			totalCount=com.tableRowReturn(tablename);
		} catch (SQLException e1) {
			System.out.println("������� ȭ�����-���̺����� ���ϱ� ����");
			resp.sendRedirect("../login.html");
			req.getSession().setAttribute("login", null);
			return;
		}
		System.out.println("hireinfo�� �� row���� : "+totalCount);
		
		//������
		int page=1;
		if(req.getParameter("page")!=null){
			page=Integer.parseInt(req.getParameter("page"));
		}
		Paging paging=new Paging();
		paging.setPage(page);
		paging.setTotalCount(totalCount);
		System.out.println("���� page : "+page);
		
		ArrayList<LMS_HireInfo_Dto> list=new ArrayList<LMS_HireInfo_Dto>();
		try {
			list=dao.employSelectAll(page);
			req.setAttribute("alist", list);
			req.setAttribute("paging", paging);
		} catch (SQLException e) {
			System.out.println("������� ȭ����� ����");
			resp.sendRedirect("../Home/login.html");
			req.getSession().setAttribute("login", null);
			return;
		}
		req.getRequestDispatcher("employ.jsp").forward(req, resp);
	}
}