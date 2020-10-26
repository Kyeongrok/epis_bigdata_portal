<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%--
�ͳ��ǻ���� ���� - ��ǥ��..
2020.02.18
--%>

<script src="<c:url value='/js/vendor/jquery/jquery-2.1.4.min.js'/>"></script>
<script type="text/javascript"	src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.1/Chart.bundle.min.js"></script>


<script src="<c:url value='/js/bigdata/portal/svc/retnFmlg/retnFmlgDetail.js'/>" defer></script>

<script src="<c:url value='/js/bigdata/portal/svc/retnFmlg/yc_a.js'/>" defer></script>
<script src="<c:url value='/js/bigdata/portal/svc/retnFmlg/yc_b.js'/>" defer></script>
<script src="<c:url value='/js/bigdata/portal/svc/retnFmlg/sc_c.js'/>" defer></script>

<link href="<c:url value='/css/bigdata/retnFmlg/presnatn/layout.css'/>" rel="stylesheet">

	<div id="skipnavigation">
		<a href="http://webdev.ucsit.co.kr/2020/BDP/farmIntent/viewResult_01.html#main_content">�������� �ٷΰ���</a>
		<a href="http://webdev.ucsit.co.kr/2020/BDP/farmIntent/viewResult_01.html#menu_top">�޴��� �ٷΰ���</a>
	</div>
<div class="wrap">

<!-- ���� GNB ���� -->
        <header>
        	<div class="gnb">
	    		<h1><a href="#">bdp</a><span class="hide">����������</span></h1>
	    		<ul class="nav">
	    			<li><a href="#"><img src="" alt="" /></a></li>
	    			<li><a href="#"><img src="" alt="" /></a></li>
	    			<li><a href="#"><img src="" alt="" /></a></li>
	    		</ul>
	    	</div><!-- //gnb -->
        </header>


<!-- �߾� ������ ���� -->
        <section class="cntWrap result" id="main_content">


        	<!-- ��ܰ��� -->
        	<div class="description">
	    		<h2><strong>�ͳ��ǻ����</strong>���� ����</h2>
	            <p class="web_ex">
	            	<strong>�ͳ��ǻ�������� ���񽺿� ���Ű��� ȯ���մϴ�.</strong><br>
	            	<span>�Է��Ͻ� ������ ������� ������ �м� �� �ͳ����� �˰��� ������ ���� ������ �ͳ������� ������ �帳�ϴ�.</span>
	            </p>
	    	</div>

	    	<div class="menuTop" id="menu_top">
	    		<ul>
	    			<li class="on"><a href="http://webdev.ucsit.co.kr/2020/BDP/farmIntent/viewResult_02.html#result02" class="scrollMove"><span>��������</span></a></li>
	    			<li><a href="http://webdev.ucsit.co.kr/2020/BDP/farmIntent/viewResult_02.html#result05" class="scrollMove"><span>������å</span></a></li>
	    			<li><a href="http://webdev.ucsit.co.kr/2020/BDP/farmIntent/viewResult_02.html#result03" class="scrollMove"><span>���ֿ���</span></a></li>
	    			<li><a href="http://webdev.ucsit.co.kr/2020/BDP/farmIntent/viewResult_02.html#result04" class="scrollMove"><span>ǰ������</span></a></li>
	    			<li><a href="http://webdev.ucsit.co.kr/2020/BDP/farmIntent/viewResult_02.html#result01" class="scrollMove"><span>�ͳ�������</span></a></li>
	    		</ul>
	    	</div>
	    	<!-- //��ܰ��� -->

	    	<div class="resultBox" id="result02">
	        	<h3>���� �ͳ� ���� ����</h3>
	        	<div class="row mt20">
        			<div class="areaList w65p">
        				<p>���Ͽ� ������ <strong>���� �ͳ� ����</strong>�Դϴ�. <br>�Ʒ��� <strong>�������� Ŭ��</strong>�Ͻø鿪 <strong>�������� �ͳ��� �� ������å</strong>�� ���� <strong>���� ���� ����, �ͳ� ���� ���� ǰ�� ����</strong>�� ����Ͽ� <strong>���� �ͳ��� ����</strong>�� �����帳�ϴ�. </p>
						<!--
							���õ� ��ư�� class = on
						-->
        				<button type="button" class="num01" onclick="location.href='./test1.do;'">
        					<span class="num">1</span>
        					<span class="txt">����ϵ� ��â�� ��â��</span>
        					<span class="btn">���� ����</span>
        				</button>
        				<button type="button" class="num02 on" onclick="location.href='./test1-2.do'">
        					<span class="num">2</span>
        					<span class="txt">���ϵ� ��õ�� ȣ���</span>
        					<span class="btn">���� ����</span>
        				</button>
        				<button type="button" class="num03" onclick="location.href='./test1-3.do'">
        					<span class="num">3</span>
        					<span class="txt">����Ư����ġ�� ���ֽ� �Ѹ���</span>
        					<span class="btn">���� ����</span>
        				</button>
        			</div>
        			<div class="w32p fr">
        				<div class="diagram">
        					<img src="/images/bigdata/svc/retnFmlg/smpl_img01_2.png" alt="���̾�׷�" style="margin:30px 0 0 30px;display:inline-block;">
        				</div>
        			</div>
        		</div>
	        </div><!-- //resultBox result02 -->


	        <div class="resultBox support" id="result05">
	        	<h3>�������� �ͳ��� �� ������å - ���ϵ� ��õ��</h3>
	        	<p class="mt20">���Ͽ� ������ <strong>���� �ͳ� ����</strong> �� ��ǿ� �´� <br><strong>�������� �ͳ��� �� ������å ����� ���</strong>�Ͽ� �Ʒ��� ���� �˷��帳�ϴ�. <br>������ Ŭ���Ͻø� ������ ����Ʈ �� �������� Ȯ���Ͻ� �� �ֽ��ϴ�.</p>
	        	<a id="btn_popup_open" href="#">
		        	<img src="/images/bigdata/svc/retnFmlg/smpl_img02_2.png" alt="���̾�׷�" style="margin:10px 0 0 0;display:inline-block;">
		        </a>
	        </div><!-- //resultBox result05 -->


	        <div class="resultBox" id="result03">
	        	<h3>���� ���� ���� ���� - ���ϵ� ��õ��</h3>
		    	<div class="tabWrap has8Tab">
                	<ul class="tabTitle">
                		<li class="tablinks on"><button type="button"><span>��������</span></button></li>
                		<li class="tablinks"><button type="button"><span>�ͳ���</span></button></li>
                		<li class="tablinks"><button type="button"><span>����</span></button></li>
                		<li class="tablinks"><button type="button"><span>����</span></button></li>
                		<li class="tablinks"><button type="button"><span>����</span></button></li>
                		<li class="tablinks"><button type="button"><span>��ȭ</span></button></li>
                		<li class="tablinks"><button type="button"><span>����</span></button></li>
                		<li class="tablinks"><button type="button"><span>�ΰ�����</span></button></li>
                	</ul>
                	<div class="tabContWrap">
                		<!-- <div class="tabcontent on">
                			<img src="/images/bigdata/svc/retnFmlg/smpl_graph19_2.png" alt="" style="margin:35px 0 0 25px;display:inline-block;">
                		</div>
                		<div class="tabcontent">
            				<img src="/images/bigdata/svc/retnFmlg/smpl_graph20.png" alt="" style="margin:35px 0 0 25px;display:inline-block;">
                		</div>
                		<div class="tabcontent">
            				<img src="/images/bigdata/svc/retnFmlg/smpl_graph21.png" alt="" style="margin:35px 0 0 25px;display:inline-block;">
                		</div>
                		<div class="tabcontent">
            				<img src="/images/bigdata/svc/retnFmlg/smpl_graph22.png" alt="" style="margin:35px 0 0 25px;display:inline-block;">
                		</div>
                		<div class="tabcontent">
            				<img src="/images/bigdata/svc/retnFmlg/smpl_graph23.png" alt="" style="margin:35px 0 0 25px;display:inline-block;">
                		</div>
                		<div class="tabcontent">
            				<img src="/images/bigdata/svc/retnFmlg/smpl_graph24.png" alt="" style="margin:35px 0 0 25px;display:inline-block;">
                		</div>
                		<div class="tabcontent">
            				<img src="/images/bigdata/svc/retnFmlg/smpl_graph25.png" alt="" style="margin:35px 0 0 25px;display:inline-block;">
                		</div>
                		<div class="tabcontent">
            				<img src="/images/bigdata/svc/retnFmlg/smpl_graph26.png" alt="" style="margin:35px 0 0 25px;display:inline-block;">
                		</div> -->
                		<div class="tabcontent on">
                			<div>
	                			<div class="graphWrap" style="width:32%;float:left;height:250px;"><canvas id="yc_a_0_0" ></canvas></div>
	                			<div class="graphWrap" style="width:65%;float:left;height:250px;"><canvas id="yc_a_0_1" ></canvas></div>
                			</div>
                			<div>
	                			<div class="graphWrap" style="width:32%;float:left;height:250px;"><canvas id="yc_a_0_2" ></canvas></div>
	                			<div class="graphWrap" style="width:65%;float:left;height:250px;"><canvas id="yc_a_0_3" ></canvas></div>
                			</div>
                		</div>
                		<div class="tabcontent">
            				<div>
	                			<div class="graphWrap" style="width:33%;float:left;height:250px;"><canvas id="yc_a_1_0" ></canvas></div>
	                			<div class="graphWrap" style="width:33%;float:left;height:250px;"><canvas id="yc_a_1_1" ></canvas></div>
	                			<div class="graphWrap" style="width:33%;float:left;height:250px;"><canvas id="yc_a_1_2" ></canvas></div>
                			</div>
                		</div>
                		<div class="tabcontent">
            				<div>
	                			<div class="graphWrap" style="width:32%;float:left;height:250px;"><canvas id="yc_a_2_0" ></canvas></div>
	                			<div class="graphWrap" style="width:65%;float:left;height:250px;"><canvas id="yc_a_2_1" ></canvas></div>
                			</div>
                		</div>
                		<div class="tabcontent">
            				<div>
	                			<div class="graphWrap" style="width:32%;float:left;height:250px;"><canvas id="yc_a_3_0" ></canvas></div>
	                			<div class="graphWrap" style="width:65%;float:left;height:250px;"><canvas id="yc_a_3_1" ></canvas></div>
                			</div>
                		</div>
                		<div class="tabcontent">
            				<div>
	                			<div class="graphWrap" style="width:32%;float:left;height:250px;"><canvas id="yc_a_4_0" ></canvas></div>
	                			<div class="graphWrap" style="width:65%;float:left;height:250px;"><canvas id="yc_a_4_1" ></canvas></div>
                			</div>
                			<div>
	                			<div class="graphWrap" style="width:32%;float:left;height:250px;"><canvas id="yc_a_4_2" ></canvas></div>
	                			<div class="graphWrap" style="width:65%;float:left;height:250px;"><canvas id="yc_a_4_3" ></canvas></div>
                			</div>
                		</div>
                		<div class="tabcontent">
            				<div>
	                			<div class="graphWrap" style="width:32%;float:left;height:250px;"><canvas id="yc_a_5_0" ></canvas></div>
	                			<div class="graphWrap" style="width:65%;float:left;height:250px;"><canvas id="yc_a_5_1" ></canvas></div>
                			</div>
                		</div>
                		<div class="tabcontent">
            				<img src="/images/bigdata/svc/retnFmlg/smpl_graph25.png" alt="" style="margin:35px 0 0 25px;display:inline-block;">
                		</div>
                		<div class="tabcontent">
            				<img src="/images/bigdata/svc/retnFmlg/smpl_graph26.png" alt="" style="margin:35px 0 0 25px;display:inline-block;">
                		</div>
                	</div>
                </div>
	        </div><!-- //resultBox result03 -->

	        <div class="resultBox" id="result04">
	        	<h3>�ͳ� ���� ���� ǰ�� ���� - ���ϵ� ��õ��</h3>
	        	<div class="tabWrap">
		        	<ul class="tabTitle itemList">
            			<!--
							���õ� ��ư�� class = on
						-->
                		<li class="tablinks on">
	        				<button type="button"><span>����</span></button>
                		</li>
                		<li class="tablinks">
	        				<button type="button"><span>��</span></button>
                		</li>
                		<li class="tablinks">
	        				<button type="button"><span>����</span></button>
                		</li>
                	</ul>
                	<div class="tabContWrap">
                		<div class="tabcontent on">
            				<div class="tabWrap has5Tab">
			                	<ul class="tabTitle">
			                		<li class="tablinks on"><button type="button"><span>����������</span></button></li>
			                		<li class="tablinks"><button type="button"><span>���Ž��� ��� ����</span></button></li>
			                		<li class="tablinks"><button type="button"><span>���ɴ뺰 ������Ȳ</span></button></li>
			                		<li class="tablinks"><button type="button"><span>����/���� ������</span></button></li>
			                		<li class="tablinks"><button type="button"><span>ǰ��ΰ�����</span></button></li>
			                	</ul>
			                	<div class="tabContWrap">
			                		<!-- <div class="tabcontent on">
			            				<img src="/images/bigdata/svc/retnFmlg/smpl_img03_2.png" alt="" style="margin:30px 0 10px 10px;display:inline-block;">
			                		</div>
			                		<div class="tabcontent">
				                		<img src="/images/bigdata/svc/retnFmlg/smpl_graph27.png" alt="" style="margin:30px 0 10px 0;display:inline-block;">
			                		</div>
			                		<div class="tabcontent">
			                		  	<img src="/images/bigdata/svc/retnFmlg/smpl_graph28.png" alt="" style="margin:30px 0 10px 50px;display:inline-block;">
			                		</div>
			                		<div class="tabcontent">
				                		<img src="/images/bigdata/svc/retnFmlg/smpl_graph29.png" alt="" style="margin:30px 0 10px 10px;display:inline-block;">
			                		</div>
			                		<div class="tabcontent">
				                		<img src="/images/bigdata/svc/retnFmlg/smpl_img07.png" alt="" style="margin:30px 0 10px 0;display:inline-block;">
			                		</div> -->
			                		<div class="tabcontent on">
			                			<img src="/images/bigdata/svc/retnFmlg/smpl_img03_2.png" alt="" style="margin:30px 0 10px 10px;display:inline-block;">
			            				<%-- <div>
				                			<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="yc_b_p_0_0" ></canvas></div>
			                			</div> --%>
			                		</div>
			                		<div class="tabcontent">
				                		<div>
				                			<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="yc_b_p_1_0" ></canvas></div>
			                			</div>
			                		</div>
			                		<div class="tabcontent">
			                			<img src="/images/bigdata/svc/retnFmlg/smpl_graph28.png" alt="" style="margin:30px 0 10px 50px;display:inline-block;">
			                		  	<%-- <div>
				                			<div class="graphWrap" style="width:48%;float:left;height:250px;"><canvas id="yc_b_p_2_0" ></canvas></div>
				                			<div class="graphWrap" style="width:48%;float:left;height:250px;"><canvas id="yc_b_p_2_1" ></canvas></div>
			                			</div> --%>
			                		</div>
			                		<div class="tabcontent">
				                		<div>
				                			<div class="graphWrap" style="width:32%;float:left;height:250px;"><canvas id="yc_b_p_3_0" ></canvas></div>
				                			<div class="graphWrap" style="width:32%;float:left;height:250px;"><canvas id="yc_b_p_3_1" ></canvas></div>
				                			<div class="graphWrap" style="width:32%;float:left;height:250px;"><canvas id="yc_b_p_3_2" ></canvas></div>
			                			</div>
			                		</div>
			                		<div class="tabcontent">
				                		<img src="/images/bigdata/svc/retnFmlg/smpl_img07.png" alt="" style="margin:30px 0 10px 0;display:inline-block;">
			                		</div>
			                	</div><!-- //tabContWrap -->
			                </div><!-- //tabWrap -->
                		</div><!-- //�� -->
                		<div class="tabcontent">
            				<div class="tabWrap has5Tab">
                                <ul class="tabTitle">
                                    <li class="tablinks on"><button type="button"><span>����������</span></button></li>
                                    <li class="tablinks"><button type="button"><span>���Ž��� ��� ����</span></button></li>
                                    <li class="tablinks"><button type="button"><span>���ɴ뺰 ������Ȳ</span></button></li>
                                    <li class="tablinks"><button type="button"><span>����/���� ������</span></button></li>
                                    <li class="tablinks"><button type="button"><span>ǰ��ΰ�����</span></button></li>
                                </ul>
                                <div class="tabContWrap">
                                    <!-- <div class="tabcontent on">
                                        <img src="/images/bigdata/svc/retnFmlg/smpl_bean01.png" alt="" style="margin:30px 0 10px 10px;display:inline-block;">
                                    </div>
                                    <div class="tabcontent">
                                        <img src="/images/bigdata/svc/retnFmlg/smpl_bean02.png" alt="" style="margin:30px 0 10px 0;display:inline-block;">
                                    </div>
                                    <div class="tabcontent">
                                        <img src="/images/bigdata/svc/retnFmlg/smpl_bean03.png" alt="" style="margin:30px 0 10px 50px;display:inline-block;">
                                    </div>
                                    <div class="tabcontent">
                                        <img src="/images/bigdata/svc/retnFmlg/smpl_bean04.png" alt="" style="margin:30px 0 10px 10px;display:inline-block;">
                                    </div>
                                    <div class="tabcontent">
                                        <img src="/images/bigdata/svc/retnFmlg/smpl_bean05.png" alt="" style="margin:30px 0 10px 0;display:inline-block;">
                                    </div> -->
                                    <div class="tabcontent on">
                                    	<img src="/images/bigdata/svc/retnFmlg/smpl_img03.png" alt="" style="margin:30px 0 10px 10px;display:inline-block;">
                                        <%-- <div>
				                			<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="yc_b_k_0_0" ></canvas></div>
			                			</div> --%>
                                    </div>
                                    <div class="tabcontent">
                                        <div>
				                			<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="yc_b_k_1_0" ></canvas></div>
			                			</div>
                                    </div>
                                    <div class="tabcontent">
                                    	<img src="/images/bigdata/svc/retnFmlg/smpl_bean03.png" alt="" style="margin:30px 0 10px 50px;display:inline-block;">
                                        <%-- <div>
				                			<div class="graphWrap" style="width:48%;float:left;height:250px;"><canvas id="yc_b_k_2_0" ></canvas></div>
				                			<div class="graphWrap" style="width:48%;float:left;height:250px;"><canvas id="yc_b_k_2_1" ></canvas></div>
			                			</div> --%>
                                    </div>
                                    <div class="tabcontent">
                                        <div>
				                			<div class="graphWrap" style="width:32%;float:left;height:250px;"><canvas id="yc_b_k_3_0" ></canvas></div>
				                			<div class="graphWrap" style="width:32%;float:left;height:250px;"><canvas id="yc_b_k_3_1" ></canvas></div>
				                			<div class="graphWrap" style="width:32%;float:left;height:250px;"><canvas id="yc_b_k_3_2" ></canvas></div>
			                			</div>
                                    </div>
                                    <div class="tabcontent">
                                        <img src="/images/bigdata/svc/retnFmlg/smpl_bean05.png" alt="" style="margin:30px 0 10px 0;display:inline-block;">
                                    </div>
                                </div><!-- //tabContWrap -->
                            </div><!-- //tabWrap -->
                		</div><!-- //���� -->
                		<div class="tabcontent">
            				<div class="tabWrap has5Tab">
                                <ul class="tabTitle">
                                    <li class="tablinks on"><button type="button"><span>����������</span></button></li>
                                    <li class="tablinks"><button type="button"><span>���Ž��� ��� ����</span></button></li>
                                    <li class="tablinks"><button type="button"><span>���ɴ뺰 ������Ȳ</span></button></li>
                                    <li class="tablinks"><button type="button"><span>����/���� ������</span></button></li>
                                    <li class="tablinks"><button type="button"><span>ǰ��ΰ�����</span></button></li>
                                </ul>
                                <div class="tabContWrap">
                                    <!-- <div class="tabcontent on">
                                        <img src="/images/bigdata/svc/retnFmlg/smpl_berry01.png" alt="" style="margin:30px 0 10px 10px;display:inline-block;">
                                    </div>
                                    <div class="tabcontent">
                                        <img src="/images/bigdata/svc/retnFmlg/smpl_berry02.png" alt="" style="margin:30px 0 10px 0;display:inline-block;">
                                    </div>
                                    <div class="tabcontent">
                                        <img src="/images/bigdata/svc/retnFmlg/smpl_berry03.png" alt="" style="margin:30px 0 10px 50px;display:inline-block;">
                                    </div>
                                    <div class="tabcontent">
                                        <img src="/images/bigdata/svc/retnFmlg/smpl_berry04.png" alt="" style="margin:30px 0 10px 10px;display:inline-block;">
                                    </div>
                                    <div class="tabcontent">
                                        <img src="/images/bigdata/svc/retnFmlg/smpl_berry05.png" alt="" style="margin:30px 0 10px 0;display:inline-block;">
                                    </div> -->
                                    <div class="tabcontent on">
                                    	<img src="/images/bigdata/svc/retnFmlg/smpl_berry01.png" alt="" style="margin:30px 0 10px 10px;display:inline-block;">
                                        <%-- <div>
				                			<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="yc_b_y_0_0" ></canvas></div>
			                			</div> --%>
                                    </div>
                                    <div class="tabcontent">
                                        <div>
				                			<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="yc_b_y_1_0" ></canvas></div>
			                			</div>
                                    </div>
                                    <div class="tabcontent">
                                    	<img src="/images/bigdata/svc/retnFmlg/smpl_berry03.png" alt="" style="margin:30px 0 10px 50px;display:inline-block;">
                                        <%-- <div>
				                			<div class="graphWrap" style="width:48%;float:left;height:250px;"><canvas id="yc_b_y_2_0" ></canvas></div>
				                			<div class="graphWrap" style="width:48%;float:left;height:250px;"><canvas id="yc_b_y_2_1" ></canvas></div>
			                			</div> --%>
                                    </div>
                                    <div class="tabcontent">
                                        <div>
				                			<div class="graphWrap" style="width:32%;float:left;height:250px;"><canvas id="yc_b_y_3_0" ></canvas></div>
				                			<div class="graphWrap" style="width:32%;float:left;height:250px;"><canvas id="yc_b_y_3_1" ></canvas></div>
				                			<div class="graphWrap" style="width:32%;float:left;height:250px;"><canvas id="yc_b_y_3_2" ></canvas></div>
			                			</div>
                                    </div>
                                    <div class="tabcontent">
                                        <img src="/images/bigdata/svc/retnFmlg/smpl_berry05.png" alt="" style="margin:30px 0 10px 0;display:inline-block;">
                                    </div>
                                </div><!-- //tabContWrap -->
                            </div><!-- //tabWrap -->
                		</div><!-- //���� -->
                	</div><!--  //tabContWrap -->
                </div><!-- //tabWrap - ���������� -->
	        </div><!-- //resultBox result04 -->



	        <div class="resultBox" id="result01">
	        	<h3>���� �ͳ��� ����</h3>
				<div class="tabWrap has5Tab">
                	<ul class="tabTitle">
                		<li class="tablinks on"><button type="button"><span>�ͳ��� �������� ����</span></button></li>
                		<li class="tablinks"><button type="button"><span class="narrowTxt">���� �ͳ��� �������� ����</span></button></li>
                		<li class="tablinks"><button type="button"><span>�ʱ⿵���� ���ǰ��</span></button></li>
                		<li class="tablinks"><button type="button"><span>����ͳ��� ���ǰ��</span></button></li>
                		<li class="tablinks"><button type="button"><span>�������� �������  ��</span></button></li>
                	</ul>
                	<div class="tabContWrap">
                		<div class="tabcontent on">
                			<img src="/images/bigdata/svc/retnFmlg/smpl_img04.png" alt="" style="margin:20px 0 10px 20px;display:inline-block;">
                		</div>
                		<div class="tabcontent">
                			<img src="/images/bigdata/svc/retnFmlg/smpl_img08.png" alt="" style="margin:20px 0 10px 20px;display:inline-block;">
                		</div>
                		<div class="tabcontent">
                			<img src="/images/bigdata/svc/retnFmlg/smpl_img09.png" alt="" style="margin:20px 0 10px 10px;display:inline-block;">
                		</div>
                		<div class="tabcontent">
                			<img src="/images/bigdata/svc/retnFmlg/smpl_img10.png" alt="" style="margin:20px 0 10px 10px;display:inline-block;">
                		</div>
                		<div class="tabcontent">
                			<img src="/images/bigdata/svc/retnFmlg/smpl_img11.png" alt="" style="margin:20px 0 10px 10px;display:inline-block;">
                		</div>
                		<%-- <div class="tabcontent on">
                			<div>
	                			<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="yc_c_0_0" ></canvas></div>
                			</div>
                		</div>
                		<div class="tabcontent">
                			<div>
	                			<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="yc_c_0_1" ></canvas></div>
                			</div>
                		</div>
                		<div class="tabcontent">
                			<div>
	                			<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="yc_c_0_2" ></canvas></div>
                			</div>
                		</div>
                		<div class="tabcontent">
                			<div>
	                			<div class="graphWrap" style="width:100%;float:left;height:250px;"><canvas id="yc_c_0_3" ></canvas></div>
                			</div>
                		</div>
                		<div class="tabcontent">
                			<img src="/images/bigdata/svc/retnFmlg/smpl_img11.png" alt="" style="margin:20px 0 10px 10px;display:inline-block;">
                		</div> --%>
                	</div>
                </div>
	        </div><!-- //resultBox result01 -->

        </section><!-- //cntWrap -->



<!-- ���� �Է� ���� -->
        <section class="myAnswer">
        	<h3>���� �Է��� �����Դϴ�.<br><span><strong>������ ����</strong>�� <strong>Ŭ��</strong>�ϸ� <strong>����</strong>�� �� �ֽ��ϴ�.</span></h3>
        	<!--
				�׸��� ����/�ݱ� Ŭ�� ���ο� ���� myBox�� class='on' �ֱ�/����.
        	-->
        	<div class="myBox on">
        		<p class="titleWrap">
        			<button type="button" class="title"><span>��������</span></button>
        			<button type="button" class="open"><span>����/�ݱ�</span></button>
        		</p>
        		<div class="row">
        			<span class="w100p">����Ư���� ���ϱ�</span>
        		</div>
        		<div class="row">
        			<span class="w48p">����</span>
        			<span class="w48p">45</span>
        		</div>
        	</div>
        	<div class="myBox on">
        		<p class="titleWrap">
        			<button type="button" class="title"><span>���Ű���</span></button>
        			<button type="button" class="open"><span>����/�ݱ�</span></button>
        		</p>
        		<div class="row">
        			<span class="w100p mint">�����</span>
        		</div>
        		<div class="row">
        			<span class="w48p">����</span>
        			<span class="w48p">41</span>
        		</div>
        		<div class="row">
        			<span class="w100p mint">�ڳ�</span>
        		</div>
        		<div class="row">
        			<span class="w48p">����</span>
        			<span class="w48p">12</span>
        		</div>
        	</div>
        	<div class="myBox on">
        		<p class="titleWrap">
        			<button type="button" class="title"><span>����ͳ�����</span></button>
        			<button type="button" class="open"><span>����/�ݱ�</span></button>
        		</p>
        		<div class="row">
        			<!-- �亯 ���� ��� class="blank" -->
        			<span class="w100p blank"></span>
        		</div>
        	</div>
        	<div class="myBox on">
        		<p class="titleWrap">
        			<button type="button" class="title"><span>������ǰ��</span></button>
        			<button type="button" class="open"><span>����/�ݱ�</span></button>
        		</p>
        		<div class="row">
        			<span class="w100p blank"></span>
        		</div>
        	</div>
        	<div class="myBox on">
        		<p class="titleWrap">
        			<button type="button" class="title"><span>�������</span></button>
        			<button type="button" class="open"><span>����/�ݱ�</span></button>
        		</p>
        		<div class="row">
        			<span class="w48p mid blank">����</span>
        			<span class="w48p low blank">�Ƿ�</span>
        		</div>
        		<div class="row">
        			<span class="w48p low blank">�б�</span>
        			<span class="w48p mid blank">����</span>
        		</div>
        		<div class="row">
        			<span class="w48p low blank" >���</span>
        			<span class="w48p hig blank">����</span>
        		</div>
        		<div class="row">
        			<span class="w48p mid blank">��ȭ</span>
        			<span class="w48p hig blank">�ͳ�������å</span>
        		</div>
        		<div class="row">
        			<span class="w48p hig blank">��깰���뿩��</span>
        			<span class="w48p low blank">�����ǰŷ�����</span>
        		</div>
        		<div class="row">
        			<span class="w48p hig blank">�����Ӵ밡��</span>
        		</div>
        		<div class="exBox">
        			<strong>����</strong>
        			<div class="row">
        				<span class="w31p hig">����</span>
	        			<span class="w31p mid">����</span>
	        			<span class="w30p low">����</span>
	        		</div>
        		</div>
        	</div>
        	<div class="btnWrap">
        		<button type="button"><span>�������</span></button>
        	</div>
        </section><!-- // -->


    </div><!-- //wrap -->
   <jsp:include page="./modal/retnFmlgPolicyModal.jsp"/>

<script>

$(function(){
	tempSrchObj.sido = '���ϵ�';
	tempSrchObj.sigungu = '��õ��';
	tempSrchObj.umd = 'ȣ���';
})
</script>