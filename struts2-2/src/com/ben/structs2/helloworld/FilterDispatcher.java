package com.ben.structs2.helloworld;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 * Servlet Filter implementation class FilterDispatcher
 */
@WebFilter("*.action")
public class FilterDispatcher implements Filter {

//    public FilterDispatcher() {
//        // TODO Auto-generated constructor stub
//    }

	public void destroy() {
		
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	@SuppressWarnings("unused")
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		//1. ��ȡservletPath
		String servletPath = req.getServletPath();
		System.out.println(servletPath);
		System.out.println(req.getContextPath());
		System.out.println(req.getRequestURI());
		System.out.println(req.getRequestURL());
		System.out.println(req.getRealPath("/"));
		
		String path = null;

		//2. �ж�servletPath �ֱ���ת
		if("/product-input.action".equals(servletPath))
		{
			path = "/WEB-INF/pages/input.jsp";
		}
		
		//3. 
		if("/product-save.action".equals(servletPath))
		{
			//(1) ��ȡ�������
			String productName = request.getParameter("productName");
			String productDesc = request.getParameter("productDesc");
			String productPrice = request.getParameter("productPrice");
			//(2) ��������Ϣ��װΪһ��Product ����
//			Product product = new Product(null,productName,productDesc,Double.parseDouble(productPrice));
			Product product = new Product();
			//(3) ִ�б������
			System.out.println("Save Product:" + product);
			product.setProductId(1001);
			product.setProductName(productName);
			product.setProductDesc(productDesc);
			product.setProductPrice(Double.parseDouble(productPrice));
			//(4) ��Product ���󱣴浽request �С� ${param.productName} -> ${requestScope.productName}
			request.setAttribute("product", product);
			
			path = "/WEB-INF/pages/details.jsp";
		}
		
		
		if(path != null)
		{
			request.getRequestDispatcher(path).forward(request, response);
			return;
		}
		
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
