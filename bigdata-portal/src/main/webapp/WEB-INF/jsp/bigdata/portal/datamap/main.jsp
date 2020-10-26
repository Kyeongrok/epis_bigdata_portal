<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>

<ul class="tab01 column2 mb20">
	<li><a href="#" class="active">기관별</a></li>
	<li><a href="#">분류체계별</a></li>
</ul>

<div class="tab03 mb20">
	<ul>
		<li><a href="#">네트워크</a></li>
		<li><a href="#" class="active">트리맵</a></li>
	</ul>
</div>

<div class="box_wh">
	<div class="tar">
		<span class="searcharea2">
			<input type="text" name="searchListWord" id="searchListWord" value="${searchListWord}" title="검색 단어 입력"/>
			<a href="#" id="searchListBtn"><i class="ico search">검색</i></a>
		</span>
		<!-- <a href="#" class="btn vat">범례</a> -->
	</div>
	<div class="datamap_tree mt30">
		<div class="treename">
			<span>기관</span>
			<span>정보시스템</span>
			<span>데이터베이스</span>
			<span>테이블</span>
		</div>
		<ul class="treetbl">
			<li>
				<a href="#" onClick="return false;" class="active">농림축산식품부</a>
				<ul class="step1">
					<li>
						<a href="javascript:void(0)">1</a>
						<ul class="step2">
							<li>
								<a href="javascript:void(0)">1-1</a>
								<ul class="step3">
									<li>
										<a href="javascript:void(0)">1-1-1</a>
										<ul class="step4">
											<li><a href="javascript:void(0)">1-1-1-1</a></li>
											<li><a href="javascript:void(0)">1-1-1-2</a></li>
											<li><a href="javascript:void(0)">1-1-1-3</a></li>
											<li><a href="javascript:void(0)">1-1-1-4</a></li>
										</ul>
									</li>
									<li><a href="javascript:void(0)">1-1-2</a></li>
									<li><a href="javascript:void(0)">1-1-3</a></li>
									<li><a href="javascript:void(0)">1-1-4</a></li>
								</ul>
							</li>
							<li>
								<a href="javascript:void(0)">1-2</a>
								<ul class="step3">
									<li><a href="javascript:void(0)">1-2-1</a></li>
									<li><a href="javascript:void(0)">1-2-2</a></li>
									<li><a href="javascript:void(0)">1-2-3</a></li>
									<li><a href="javascript:void(0)">1-2-4</a></li>
								</ul>
							</li>
							<li>
								<a href="javascript:void(0)">1-3</a>
								<ul class="step3">
									<li><a href="javascript:void(0)">1-3-1</a></li>
									<li><a href="javascript:void(0)">1-3-2</a></li>
								</ul>
							</li>
							<li><a href="javascript:void(0)">1-4</a></li>
						</ul>
					</li>
					<li>
						<a href="javascript:void(0)">2</a>
						<ul class="step2">
							<li><a href="javascript:void(0)">2-1</a></li>
							<li>
								<a href="javascript:void(0)">2-2</a>
								<ul class="step3">
									<li><a href="javascript:void(0)">2-2-1</a></li>
									<li><a href="javascript:void(0)">2-2-2</a></li>
									<li><a href="javascript:void(0)">2-2-3</a></li>
									<li><a href="javascript:void(0)">2-2-4</a></li>
								</ul>
							</li>
							<li><a href="javascript:void(0)">2-3</a></li>
							<li><a href="javascript:void(0)">2-4</a></li>
						</ul>
					</li>
					<li>
						<a href="javascript:void(0)">3</a>
						<ul class="step2">
							<li><a href="javascript:void(0)">3-1</a></li>
							<li><a href="javascript:void(0)">3-2</a></li>
							<li><a href="javascript:void(0)">3-3</a></li>
							<li><a href="javascript:void(0)">3-4</a></li>
						</ul></li>
					<li>
						<a href="javascript:void(0)">4</a>
						<ul class="step2">
							<li>
								<a href="javascript:void(0)">4-1</a>
								<ul class="step3">
									<li><a href="javascript:void(0)">4-1-1</a></li>
									<li><a href="javascript:void(0)">4-1-2</a></li>
									<li>
										<a href="javascript:void(0)">4-1-3</a>
										<ul class="step4">
											<li><a href="javascript:void(0)">4-1-3-1</a></li>
											<li><a href="javascript:void(0)">4-1-3-2</a></li>
											<li><a href="javascript:void(0)">4-1-3-3</a></li>
											<li><a href="javascript:void(0)">4-1-3-4</a></li>
											<li><a href="javascript:void(0)">4-1-3-5</a></li>
											<li><a href="javascript:void(0)">4-1-3-6</a></li>
											<li><a href="javascript:void(0)">4-1-3-7</a></li>
											<li><a href="javascript:void(0)">4-1-3-8</a></li>
											<li><a href="javascript:void(0)">4-1-3-9</a></li>
										</ul>
									</li>
									<li><a href="javascript:void(0)">4-1-4</a></li>
									<li><a href="javascript:void(0)">4-1-5</a></li>
									<li><a href="javascript:void(0)">4-1-6</a></li>
									<li><a href="javascript:void(0)">4-1-7</a></li>
									<li><a href="javascript:void(0)">4-1-8</a></li>
									<li><a href="javascript:void(0)">4-1-9</a></li>
									<li><a href="javascript:void(0)">4-1-10</a></li>
									<li><a href="javascript:void(0)">4-1-11</a></li>
									<li><a href="javascript:void(0)">4-1-12</a></li>
									<li><a href="javascript:void(0)">4-1-13</a></li>
									<li><a href="javascript:void(0)">4-1-14</a></li>
									<li><a href="javascript:void(0)">4-1-15</a></li>
									<li><a href="javascript:void(0)">4-1-16</a></li>
									<li><a href="javascript:void(0)">4-1-17</a></li>
									<li><a href="javascript:void(0)">4-1-18</a></li>
									<li><a href="javascript:void(0)">4-1-19</a></li>
									<li><a href="javascript:void(0)">4-1-20</a></li>
								</ul>
							</li>
							<li><a href="javascript:void(0)">4-2</a></li>
							<li><a href="javascript:void(0)">4-3</a></li>
							<li><a href="javascript:void(0)">4-4</a></li>
							<li><a href="javascript:void(0)">4-5</a></li>
							<li><a href="javascript:void(0)">4-6</a></li>
							<li><a href="javascript:void(0)">4-7</a></li>
							<li><a href="javascript:void(0)">4-8</a></li>
						</ul>
					</li>
					<li><a href="javascript:void(0)">5</a></li>
					<li><a href="javascript:void(0)">6</a></li>
					<li><a href="javascript:void(0)">7</a></li>
					<li><a href="javascript:void(0)">8</a></li>
					<li><a href="javascript:void(0)">9</a></li>
					<li><a href="javascript:void(0)">10</a></li>
					<li><a href="javascript:void(0)">11</a></li>
					<li><a href="javascript:void(0)">12</a></li>
					<li><a href="javascript:void(0)">13</a></li>
					<li><a href="javascript:void(0)">14</a></li>
					<li><a href="javascript:void(0)">15</a></li>
					<li><a href="javascript:void(0)">16</a></li>
					<li><a href="javascript:void(0)">17</a></li>
					<li><a href="javascript:void(0)">18</a></li>
					<li><a href="javascript:void(0)">19</a></li>
					<li><a href="javascript:void(0)">20</a></li>
				</ul>
			</li>
		</ul>
	</div>
</div>