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

<script src="<c:url value='/js/bigdata/portal/svc/retnFmlg/graph.js'/>" defer></script>


<link href="<c:url value='/css/bigdata/retnFmlg/presnatn/layout.css'/>" rel="stylesheet">

	<div id="skipnavigation">
		<a href="http://webdev.ucsit.co.kr/2020/BDP/farmIntent/viewResult_01.html#main_content">�������� �ٷΰ���</a>
		<a href="http://webdev.ucsit.co.kr/2020/BDP/farmIntent/viewResult_01.html#menu_top">�޴��� �ٷΰ���</a>
	</div>
<div class="wrap">

<!-- ���� GNB ���� -->
        <header>
        	<div class="gnb">
	    		<h1><a href="http://webdev.ucsit.co.kr/2020/BDP/farmIntent/viewResult_01.html#">bdp</a><span class="hide">����������</span></h1>
	    		<ul class="nav">
	    			<li><a href="http://webdev.ucsit.co.kr/2020/BDP/farmIntent/viewResult_01.html#"><img src="/images/bigdata/svc/navicon01.png" alt=""></a></li>
	    			<li><a href="http://webdev.ucsit.co.kr/2020/BDP/farmIntent/viewResult_01.html#"><img src="/images/bigdata/svc/navicon02.png" alt=""></a></li>
	    			<li><a href="http://webdev.ucsit.co.kr/2020/BDP/farmIntent/viewResult_01.html#"><img src="/images/bigdata/svc/navicon03.png" alt=""></a></li>
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
	    			<li><a href="http://webdev.ucsit.co.kr/2020/BDP/farmIntent/viewResult_02.html#result01" class="scrollMove"><span>ǰ��м�</span></a></li>
	    			<li><a href="http://webdev.ucsit.co.kr/2020/BDP/farmIntent/viewResult_02.html#result05" class="scrollMove"><span>������å</span></a></li>
	    			<li><a href="http://webdev.ucsit.co.kr/2020/BDP/farmIntent/viewResult_02.html#result03" class="scrollMove"><span>���ֿ���</span></a></li>
	    			<li><a href="http://webdev.ucsit.co.kr/2020/BDP/farmIntent/viewResult_02.html#result04" class="scrollMove"><span>ǰ������</span></a></li>

	    		</ul>
	    	</div>
	    	<!-- //��ܰ��� -->


	    	<div class="resultBox" id="result01">
	        	<h3>�������� �� ǰ�� �м� - ���ϵ� ���ֽ�</h3>
	        	<div class="tabWrap">
		        	<ul class="tabTitle itemList">
            			<!--
							���õ� ��ư�� class = on
						-->
                		<li class="tablinks on">
	        				<button type="button"><span>����</span></button>
                		</li>
                	</ul>
                	<div class="tabContWrap">
                		<div class="tabcontent on">
            				<div class="tabWrap has5Tab">
			                	<ul class="tabTitle">
			                		<li class="tablinks on"><button type="button"><span>����� ��Ȳ</span></button></li>
			                		<li class="tablinks"><button type="button"><span>����������</span></button></li>
			                		<li class="tablinks"><button type="button"><span>�ʱ� ������ ������Ȳ</span></button></li>
<!-- 			                		<li class="tablinks"><button type="button"><span>�������������</span></button></li> -->
<!-- 			                		<li class="tablinks"><button type="button"><span>���ɺ� ���������</span></button></li> -->
			                	</ul>
			                	<div class="tabContWrap">
			                		<div class="tabcontent on">
			            				<img src="/images/bigdata/svc/retnFmlg/smpl_img05.png" alt="" style="margin:20px 0 10px 30px;display:inline-block;">
			                		</div>

			                		<!-- ���������� -->
			                		<div class="tabcontent">
			                			<div>
			                				<div style="width:69%;float:left;">
			                					<canvas id="graph_t1_0" height="250"></canvas>
			                				</div>
			                				<div style="width:29%;float:left;">
			                					<canvas id="graph_t1_1" height="250" ></canvas>
			                				</div>
			                			</div>
			            				<!-- <img src="/images/bigdata/svc/retnFmlg/smpl_img12.png" alt="" style="margin:20px 0 10px 30px;display:inline-block;"> -->
<!-- 				                		<h5>����������</h5> -->
			                		</div>

			                		<!-- �ʱ� ������ ������Ȳ -->
			                		<div class="tabcontent">
			                			<div style="width:33%;float:left">
			                				<canvas id="graph_t2_0" height="250"></canvas>
			                			</div>
			                			<div style="width:33%;float:left">
			                				<canvas id="graph_t2_1" height="250"></canvas>
			                			</div>
			                			<div style="width:33%;float:left">
			                				<canvas id="graph_t2_2" height="250"></canvas>
			                			</div>
<!-- 			                		<h5>�������������</h5> -->
			            				<!-- <img src="/images/bigdata/svc/retnFmlg/smpl_img13.png" alt="" style="margin:20px 0 10px 30px;display:inline-block;"> -->
			                		</div>
			                		<div class="tabcontent">
				                		<h5>���ɺ� ���������</h5>
			                		</div>
			                		<div class="tabcontent">
				                		<h5>�ʱ� ������ ������Ȳ</h5>
			                		</div>
			                	</div><!-- //tabContWrap -->
			                </div><!-- //tabWrap -->
                		</div><!-- //�� -->
                		<div class="tabcontent">
            				����
                		</div><!-- //���� -->
                		<div class="tabcontent">
            				����
                		</div><!-- //���� -->
                	</div><!--  //tabContWrap -->
                </div><!-- //tabWrap - ���������� -->
	        </div><!-- //resultBox result04 -->


	        <div class="resultBox support" id="result05">
	        	<h3>�������� �ͳ��� �� ������å - ���ϵ� ���ֽ�</h3>
	        	<p class="mt20">���Ͽ� ������ <strong>���� �ͳ� ����</strong> �� ��ǿ� �´� <br><strong>�������� �ͳ��� �� ������å ����� ���</strong>�Ͽ� �Ʒ��� ���� �˷��帳�ϴ�. <br>������ Ŭ���Ͻø� ������ ����Ʈ �� �������� Ȯ���Ͻ� �� �ֽ��ϴ�.</p>
	        	<a id="btn_popup_open" href="#">
		        	<img src="/images/bigdata/svc/retnFmlg/smpl_img02_B.png" alt="���̾�׷�" style="margin:10px 0 0 0;display:inline-block;">
		        </a>
	        </div><!-- //resultBox result05 -->


	        <div class="resultBox" id="result03">
	        	<h3>���� ���� ���� ���� - ���ϵ� ���ֽ�</h3>
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
                		<div class="tabcontent">
                			<div>
                				<div style="width:29%;float:left;">
                					<canvas id="sj_b_t0_0" height="250"></canvas>
                				</div>
                				<div style="width:69%;float:left;">
                					<canvas id="sj_b_t0_1" height="250"></canvas>
                				</div>
                				<div style="width:29%;float:left;">
                					<canvas id="sj_b_t0_2" height="250"></canvas>
                				</div>
                				<div style="width:69%;float:left;">
                					<canvas id="sj_b_t0_3" height="250"></canvas>
                				</div>
                			</div>
							<!-- <img src="/images/bigdata/svc/retnFmlg/smpl_graph30.png" alt="" style="margin:30px 0 10px 0;display:inline-block;"> -->
                		</div>
                		<div class="tabcontent">
            				<div class="lineBox mt20">
            					<p class="title">
            						<span>���ɴ뺰 �ͳ��� ����(��) </span>
            						<span style="margin-left:110px;">�ͳ��� �������� ���� </span>
            						<span style="margin-left:160px;">�ͳ��� ���ǰ�� ���� </span>
            					</p>
            					<div class="">
            						<img src="/images/bigdata/svc/retnFmlg/smpl_graph09.png" alt="" style="margin:35px 0 0 25px;display:inline-block;">
                				</div>
                			</div>
                		</div>
                		<div class="tabcontent">
                			<div class="row">
	                			<div class="w27p">
	                				<h5>�����ü��� ���ٽð�</h5>
	                				<div class="lineBox">
	                					<p class="title">�ڰ��� �̿�� �ҿ�ð�(��)</p>
		                				<div class="">
		                					<img src="/images/bigdata/svc/retnFmlg/smpl_graph14.png" alt="" style="margin:50px 0 0 30px;display:inline-block;">
		                				</div>
		                			</div>
	                			</div>
	                			<div class="w69p fr">
	                				<h5>�ȼ��� ���鵿�� �����ü� ���ټ� ��</h5>
	                				<div class="lineBox">
	                					<div class="tabWrap typeB">
	                						<ul class="tabTitle">
						                		<li class="tablinks on"><button type="button"><span>�ʵ��б�</span></button></li>
						                		<li class="tablinks"><button type="button"><span>���б�</span></button></li>
						                		<li class="tablinks"><button type="button"><span>����б�</span></button></li>
						                	</ul>
						                	<div class="tabContWrap">
						                		<div class="tabcontent on">
						                			<img src="/images/bigdata/svc/retnFmlg/smpl_graph15.png" alt="" style="margin:3px 0 0 25px;display:inline-block;">
						                		</div>
						                		<div class="tabcontent">
						                			<img src="/images/bigdata/svc/retnFmlg/smpl_graph16.png" alt="" style="margin:3px 0 0 25px;display:inline-block;">
						                		</div>
						                		<div class="tabcontent">
						                			<img src="/images/bigdata/svc/retnFmlg/smpl_graph17.png" alt="" style="margin:3px 0 0 25px;display:inline-block;">
						                		</div>
						                	</div>

		                				</div>
		                			</div>
	                			</div>
	                		</div>
                		</div>
                		<div class="tabcontent on">
                			<img src="/images/bigdata/svc/retnFmlg/smpl_graph18.png" alt="" style="margin:30px 0 10px 0;display:inline-block;">
                		</div>
                		<div class="tabcontent">
                			<h5>����</h5>

                		</div>
                		<div class="tabcontent">
                			<h5>��ȭ</h5>

                		</div>
                		<div class="tabcontent">
                			<h5>����</h5>

                		</div>
                		<div class="tabcontent">
                			<h5>�ΰ�����</h5>

                		</div>
                	</div>
                </div>
	        </div><!-- //resultBox result03 -->



	        <div class="resultBox" id="result04">
	        	<h3>�ͳ� ���� ���� ǰ�� ���� - ���ϵ� ���ֽ�</h3>
	        	<div class="tabWrap">
		        	<ul class="tabTitle itemList">
            			<!--
							���õ� ��ư�� class = on
						-->
                		<li class="tablinks on">
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
			                		<div class="tabcontent on">
			            				<img src="/images/bigdata/svc/retnFmlg/smpl_img03.png" alt="" style="margin:30px 0 10px 0;display:inline-block;">
			                		</div>
			                		<div class="tabcontent">
				                		<h5>���Ž��� ��� ����</h5>
			                		</div>
			                		<div class="tabcontent">                			<h5>���ɴ뺰 ������Ȳ</h5>
			                		</div>
			                		<div class="tabcontent">
				                		<h5>����/���� ������</h5>
			                		</div>
			                		<div class="tabcontent">
				                		<h5>ǰ��ΰ�����</h5>
			                		</div>
			                	</div><!-- //tabContWrap -->
			                </div><!-- //tabWrap -->
                		</div><!-- //�� -->
                		<div class="tabcontent">

                		</div><!-- //���� -->
                		<div class="tabcontent">

                		</div><!-- //���� -->
                	</div><!--  //tabContWrap -->
                </div><!-- //tabWrap - ���������� -->
	        </div><!-- //resultBox result04 -->



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
        			<span class="w100p">����Ư���� ������</span>
        		</div>
        		<div class="row">
        			<span class="w48p">����</span>
        			<span class="w48p">59</span>
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
        			<span class="w48p">55</span>
        		</div>
<!--         		<div class="row"> -->
<!--         			<span class="w100p mint">�ڳ�</span> -->
<!--         		</div> -->
<!--         		<div class="row"> -->
<!--         			<span class="w48p">����</span> -->
<!--         			<span class="w48p">12</span> -->
<!--         		</div> -->
        	</div>
        	<div class="myBox on">
        		<p class="titleWrap">
        			<button type="button" class="title"><span>����ͳ�����</span></button>
        			<button type="button" class="open"><span>����/�ݱ�</span></button>
        		</p>
        		<div class="row">
        			<!-- �亯 ���� ��� class="blank" -->
        			<span class="w100p">���ϵ� ���ֽ�</span>
        		</div>
        	</div>
        	<div class="myBox on">
        		<p class="titleWrap">
        			<button type="button" class="title"><span>������ǰ��</span></button>
        			<button type="button" class="open"><span>����/�ݱ�</span></button>
        		</p>
        		<div class="row">
        			<span class="w100p">����</span>
        		</div>
        	</div>
        	<div class="myBox on">
        		<p class="titleWrap">
        			<button type="button" class="title"><span>�������</span></button>
        			<button type="button" class="open"><span>����/�ݱ�</span></button>
        		</p>
        		<div class="row">
        			<span class="w48p hig">����</span>
        			<span class="w48p hig">�Ƿ�</span>
        		</div>
        		<div class="row">
        			<span class="w48p low">�б�</span>
        			<span class="w48p low">����</span>
        		</div>
        		<div class="row">
        			<span class="w48p low " >���</span>
        			<span class="w48p low ">����</span>
        		</div>
        		<div class="row">
        			<span class="w48p low ">��ȭ</span>
        			<span class="w48p mid">�ͳ�������å</span>
        		</div>
        		<div class="row">
        			<span class="w48p low ">��깰���뿩��</span>
        			<span class="w48p low ">�����ǰŷ�����</span>
        		</div>
        		<div class="row">
        			<span class="w48p low ">�����Ӵ밡��</span>
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
	tempSrchObj.sigungu = '���ֽ�';
// 	tempSrchObj.umd = '�Ѹ���';
})