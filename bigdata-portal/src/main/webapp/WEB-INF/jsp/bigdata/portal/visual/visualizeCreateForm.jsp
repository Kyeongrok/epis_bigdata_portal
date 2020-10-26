<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:import url="/WEB-INF/jsp/layout/grid_chart.jsp" />
<c:import url="/WEB-INF/jsp/layout/thumb_script.jsp" />
<script type="text/javascript" src="<c:url value="/js/vendor/jquery/jquery.form.min.js" />" charset="utf-8"></script>
<script type="text/javascript" src="<c:url value="/js/vendor/spectrum/spectrum.js" />" charset="utf-8"></script>
<link rel="stylesheet" href="<c:url value="/js/vendor/spectrum/spectrum.css" />" type="text/css" />



<form id="visFrm" name="visFrm" action="<c:url value="./insert.do" />" method="post" onsubmit="return false;">
<input type="hidden" name="visChartX" />
<input type="hidden" name="visChartY" />
<input type="hidden" name="visChartZ" />

<input type="hidden" name="visChartXLabel" />
<input type="hidden" name="visChartYLabel" />
<input type="hidden" name="visChartZLabel" />


<div class="tit_merge">
	<input type="text" name="visTitle" id="vis_title" value="" placeholder="시각화 제목을 입력하세요." />
</div>
<div class="w100p">
	<table class="tbl02 column2 anaform" summary="시각화 등록폼입니다.">
		<caption>시각화 등록폼 테이블</caption>
		<tbody>
			<tr>
				<th>데이터명</th>
				<td class="ana_dname">
					<a href="#" id="btn_data_select" class="btn redline">데이터선택</a>
					<input type="text" name="" id="ana_data_name" value="" readonly>
				</td>
			</tr>
			<tr>
				<th>카테고리</th>
				<td>
					<select name="visCate1">
						<option value="">품목별 분류</option>
						<c:forEach var="row" items="${codeItem}" varStatus="status">
							<option value="${row.code}" <c:if test="${row.code == param.searchType1}">selected="selected"</c:if>>${row.codeNm}</option>
						</c:forEach>
					</select>
					<select name="visCate2">
						<option value="">영역별 분류</option>
						<c:forEach var="row" items="${codeType}" varStatus="status">
							<option value="${row.code}" <c:if test="${row.code == param.searchType2}">selected="selected"</c:if>>${row.codeNm}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<th>공개여부</th>
				<td>
					<select name="visOpenAt">
						<option value="Y">공개</option>
						<option value="N">비공개</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>데이터출처</th>
				<td><input type="text" name="visSource" class="w100p"></td>
			</tr>
			<tr>
				<th>시각화설명</th>
				<td><textarea name="visChartDesc" class="w100p bdbox"></textarea></td>
			</tr>
		</tbody>
	</table>
</div>
</form>

<h3><i class="ico dataset"></i>데이터셋</h3>

<div id="data_sheet_table" class="databox"></div>


<!-- S : visualization -->
<h3><i class="ico visualization"></i>시각화</h3>
<div class="wrap_vz">
	<ul class="vz_type" id="select_ctype">
		<li><input type="radio" id="v1" name="vz_type" value="1" data-cname="선 차트(Line)" data-x-header="*X축 기준" data-x-multi="false" data-y-header="*Y축 기준" data-y-multi="true" checked><label for="v1"><i class="ico vz1"></i>Line</label></li>
		<li><input type="radio" id="v2" name="vz_type" value="2" data-cname="바 차트(Bar)" data-x-header="*X축 기준" data-x-multi="false" data-y-header="*Y축 기준" data-y-multi="true"><label for="v2"><i class="ico vz2"></i>Bar</label></li>
		<li><input type="radio" id="v3" name="vz_type" value="3" data-cname="영역 차트(Area)" data-x-header="*X축 기준" data-x-multi="false" data-y-header="*Y축 기준" data-y-multi="true"><label for="v3"><i class="ico vz3"></i>Area</label></li>
		<li><input type="radio" id="v4" name="vz_type" value="4" data-cname="라인+바 차트(Line + Bar)" data-x-header="*X축 기준" data-x-multi="false" data-y-header="*Y축 기준" data-y-multi="true"><label for="v4"><i class="ico vz4"></i>Line + Bar</label></li>
		<li><input type="radio" id="v5" name="vz_type" value="5" data-cname="파이 차트(Pie)" data-x-header="*구분" data-x-multi="false" data-y-header="*값 기준(숫자)" data-y-multi="false"><label for="v5"><i class="ico vz5"></i>Pie</label></li>
		<li><input type="radio" id="v6" name="vz_type" value="6" data-cname="버블 차트(Bubble)" data-x-header="*X축 기준" data-x-multi="true" data-y-header="*Y축 기준" data-y-multi="true" data-z-header="*값 기준(숫자)" data-z-multi="true"><label for="v6"><i class="ico vz6"></i>Bubble</label></li>
		<li><input type="radio" id="v7" name="vz_type" value="7" data-cname="산점도(Scatter)" data-x-header="*X축 기준" data-x-multi="false" data-y-header="*Y축 기준" data-y-multi="false" data-z-header="구분" data-z-multi="false" data-z-null="true"><label for="v7"><i class="ico vz7"></i>Scatter</label></li>
		<li><input type="radio" id="v8" name="vz_type" value="8" data-cname="산점도행렬(Scatter Matrix)" data-x-header="*구분" data-x-multi="false" data-y-header="*X/Y 기준(숫자)" data-y-multi="true"><label for="v8"><i class="ico vz8"></i>Scatter Matrix</label></li>
		<li><input type="radio" id="v9" name="vz_type" value="21" data-cname="지도(Map)" data-x-header="*지역기준" data-x-multi="false" data-z-header="*값 기준" data-z-multi="false" data-z-null="false"><label for="v9"><i class="ico vz9"></i>Map</label></li>
	</ul>
	
	<div id="thumbdiv" style="width:0;height:0;overflow:hidden;"><!-- 섬네일 영역 --></div>
	<div id="chartdiv" class="vz_graph"><!-- 그래프 영역 --></div>
	
	<div id="vis_set" class="vz_set">
		<dl class="vis">
			<dt id="geo_header">지역기준값</dt>
			<dd id="geo_selector">
				<select name="chartOption.map.geoType">
					<option value="pnu">지역코드</option>
					<option value="geocode">좌표</option>
					<option value="geogroup">좌표(클러스터)</option>
				</select>
			</dd>
			<!-- 그래프 축 선택 컬럼 -->
			<dt id="x_header"></dt>
			<dd id="x_selector"></dd>
			<dt id="y_header"></dt>
			<dd id="y_selector"></dd>
			<dt id="z_header"></dt>
			<dd id="z_selector"></dd>
		</dl>
		
		<div id="vis_option">
			<dl class="vis" data-section="title" data-chart-use="[1,2,3,4,5,6,7,8]">
				<dt>그래프 타이틀</dt>	
				<dd><input type="text" name="chartOption.title" id="vis_title" placeholder="그래프 타이틀" /></dd>
				<dt>차트여백 <em>(좌, 우, 상, 하)</em></dt>	
				<dd class="hform column4">
					<input type="text" name="chartOption.marginLeft" placeholder="왼쪽" data-default-value="60" /><input 
							type="text" name="chartOption.marginRight" placeholder="오른쪽" data-default-value="20" /><input 
							type="text" name="chartOption.marginTop" placeholder="위" data-default-value="20" /><input 
							type="text" name="chartOption.marginBottom" placeholder="아래" data-default-value="20" />
				</dd>
			</dl>
	
			<dl class="vis" data-section="colortheme" data-chart-use="[1,2,3,4,5,6,7,8]">
				<dt>색상테마</dt>
				<dd class="btn_radio">
					<span class="disinblock">
						<input type="radio" id="c1" name="chartOption.theme" value="light" checked><label for="c1"><span></span>Light</label>
						<input type="radio" id="c2" name="chartOption.theme" value="patterns"><label for="c2"><span></span>Pattern</label>
					</span>
					<ul id="color_palette">
						<li><a class="colorpicker" id="color_choice_btn"><img src="/images/bigdata/icon/ico_picker.png" alt="색상선택" /></a><input type="text" id="color_choice" /></li>
					</ul>
				</dd>
			</dl>
			
			<dl class="vis" data-section="regend" data-chart-use="[1,2,3,4,5,6,7]">
				<dt>
					<span>범례</span>
					<span class="check"><input type="checkbox" name="chartOption.regend.display" value="Y" data-default-checked="true" id="reg_dis" /><label for="reg_dis"><span></span>사용</label></span>
				</dt>
				<dd>
					<ul class="stit">
						<li class="tit">위치</li>
						<li>
							<select name="chartOption.regend.position">
								<option value="left">왼쪽</option>
								<option value="right" data-default-selected="true">오른쪽</option>
								<option value="top">위</option>
								<option value="bottom">아래</option>
							</select>
						</li>
					</ul>
				</dd>
			</dl>
			
			<dl class="vis" data-section="xlabel" data-chart-use="[1,2,3,4,6,7]">
				<dt>X AXIS</dt>
				<dd>
					<ul class="stit">
						<li class="tit">라벨</li>
						<li><input type="text" name="chartOption.categoryAxis.title"/></li>
						<li class="tit">라벨회전</li>
						<li><input type="text" name="chartOption.categoryAxis.labelRotation" class="spinner" data-spinner-start="-90" data-spinner-end="90" data-spinner-step="5" data-default-value="0" /></li>
						<li class="tit">시계열</li>
						<li class="check">
							<input type="checkbox" id="parse_date" name="chartOption.categoryAxis.parseDates" value="Y" />
							<label for="parse_date"><span></span>사용</label>
						</li>
						<li class="tit parse_date_checked">날짜포멧</li>
						<li class="parse_date_checked"><input type="text" name="chartOption.dataDateFormat" data-default-value="YYYY-MM-DD" /></li>
						<li class="tit parse_date_checked">표시단위</li>
						<li class="parse_date_checked">
							<select name="chartOption.categoryAxis.minPeriod">
								<option value="DD" data-default-selected="true">일단위</option>
								<option value="MM" >월단위</option>
								<option value="YYYY" >년단위</option>
								<option value="hh" >시간단위</option>
								<option value="mm" >분단위</option>
								<option value="ss" >초단위</option>
							</select>
						</li>
						<li class="tit parse_date_checked">커서포멧</li>
						<li class="parse_date_checked"><input type="text" name="chartOption.chartCursor.categoryBalloonDateFormat" data-default-value="YYYY-MM-DD" /></li>
					</ul>
				</dd>
			</dl>
			
			<dl  class="vis" data-section="ylabel" data-chart-use="[1,2,3,4,6,7]">
				<dt>Y AXIS</dt>
				<dd>
					<ul class="stit">
						<li class="tit">라벨</li><li><input type="text" name="chartOption.valueAxes[0].title"/></li>
						<li class="tit">값표시</li><li class="check"><input type="checkbox" name="chartOption.graph.displayLabelText" value="Y" id="gra_dis_label"/><label for="gra_dis_label"><span></span>표시</label></li>
					</ul>
				</dd>
			</dl>
			<dl  class="vis" data-section="ylabel_multi" data-chart-use="[1,2,3,4]">
				<dt>Y AXIS <em>(2)</em></dt>
				<dd>
					<ul class="stit">
						<li class="tit">라벨</li><li><input type="text" name="chartOption.valueAxes[1].title"/></li>
						<li class="tit">위치</li><li><select name="chartOption.valueAxes[1].position" class="w2">
								<option value="left">왼쪽</option>
								<option value="right" data-default-selected="true">오른쪽</option>
							</select>
							<input type="text" name="chartOption.valueAxes[1].offset" class="spinner w2" data-spinner-start="0" data-spinner-step="5" data-default-value="" placeholder="Offset" /></li>
						<li class="tit">적용컬럼</li><li><select name="chartOption.valueAxes[1].graph" class="selected_y" multiple></select></li>
					</ul>
				</dd>
				<dt>Y AXIS <em>(3)</em></dt>
				<dd>
					<ul class="stit">
						<li class="tit">라벨</li><li><input type="text" name="chartOption.valueAxes[2].title"/></li>
						<li class="tit">위치</li><li><select name="chartOption.valueAxes[2].position" class="w2">
								<option value="left">왼쪽</option>
								<option value="right" data-default-selected="true">오른쪽</option>
							</select>
							<input type="text" name="chartOption.valueAxes[2].offset" class="spinner w2" data-spinner-start="0" data-spinner-step="5" data-default-value="" placeholder="Offset" /></li>
						<li class="tit">적용컬럼</li><li><select name="chartOption.valueAxes[2].graph" class="selected_y" multiple></select></li>
					</ul>
				</dd>
			</dl>
			
			<dl class="vis" data-section="line_style" data-chart-use="[1,3,4]">
				<dt>선 스타일</dt>
				<dd>
					<ul class="stit">
						<li class="tit">유형</li>
						<li class="marker_type">
							<input type="radio" name="chartOption.graph.type" value="line" data-default-checked="true" id="gra_type_line" /><label for="gra_type_line"><span></span>기본</label>
							<input type="radio" name="chartOption.graph.type" value="smoothedLine" id="gra_type_smoo" /><label for="gra_type_smoo"><span></span>부드러운</label>
							<input type="radio" name="chartOption.graph.type" value="step" id="gra_type_step" /><label for="gra_type_step"><span></span>스텝</label>
						</li>
						<li class="tit">선굵기</li><li><input type="text" name="chartOption.graph.lineThickness" class="spinner" data-spinner-start="1" data-spinner-end="10" data-spinner-step="1" data-default-value="2" /></li>
						<li class="tit">마커크기</li><li><input type="text" name="chartOption.graph.bulletSize" class="spinner" data-spinner-start="0" data-spinner-end="20" data-spinner-step="1" data-default-value="6" /></li>
						<li class="tit">마커모양</li>
						<li class="marker_shape">
							<input type="radio" name="chartOption.graph.bullet" data-default-checked="true" value="round" id="gra_bull_round"/><label for="gra_bull_round"><span></span><img src="/images/bigdata/icon/ico_marker01.jpg" /></label>
							<input type="radio" name="chartOption.graph.bullet" value="square" id="gra_bull_squa"/><label for="gra_bull_squa"><span></span><img src="/images/bigdata/icon/ico_marker02.jpg" /></label>
							<input type="radio" name="chartOption.graph.bullet" value="triangleUp" id="gra_bull_triu"/><label for="gra_bull_triu"><span></span><img src="/images/bigdata/icon/ico_marker03.jpg" /></label>
							<input type="radio" name="chartOption.graph.bullet" value="triangleDown" id="gra_bull_trid"/><label for="gra_bull_trid"><span></span><img src="/images/bigdata/icon/ico_marker04.jpg" /></label>
							<input type="radio" name="chartOption.graph.bullet" value="diamond" id="gra_bull_diam"/><label for="gra_bull_diam"><span></span><img src="/images/bigdata/icon/ico_marker05.jpg" /></label>
							<input type="radio" name="chartOption.graph.bullet" value="mix" id="gra_bull_mix"/><label for="gra_bull_mix"><span>MIX</span></label>
							<input type="radio" name="chartOption.graph.bullet" value="order" id="gra_bull_order"/><label for="gra_bull_order"><span>ROTATE</span></label>
						</li>
					</ul>
				</dd>
			</dl>
			<dl  class="vis" data-section="convert_bar" data-chart-use="[4]">
				<dt>막대컬럼</dt>
				<dd>
					<select name="chartOption.graph.types" class="selected_y" multiple></select>
					<em>* 선택하지 않으면 라인으로 표시</em>
				</dd>
			</dl>
			<dl class="vis" data-section="bar_width" data-chart-use="[2,4]">
				<dt>막대 스타일</dt>
				<dd><input type="text" name="chartOption.graph.columnWidth" class="spinner" data-spinner-start="0.1" data-spinner-end="1" data-spinner-step="0.1" data-default-value="0.5" /></dd>
				<dt>누적/겹침</dt>
				<dd>
					<input type="checkbox" name="chartOption.graph.stack" value="Y" id="gra_stack"/><label for="gra_stack"><span></span>막대누적</label>
					<input type="checkbox" name="chartOption.graph.clustered"  value="Y" id="gra_clus"/><label for="gra_clus" class="ml20"><span></span>막대 겹침</label>
				</dd>
			</dl>
			<dl class="vis" data-section="bar_rotate" data-chart-use="[2]">
				<dt>막대방향</dt>
				<dd>
					<input type="checkbox" name="chartOption.rotate" value="Y" id="rot"/><label for="rot"><span></span>90˚회전</label>
				</dd>
			</dl>
		
			<dl class="vis" data-section="pie_radius" data-chart-use="[5]">
				<dt>스타일</dt>
				<dd>
					<ul class="mid stit">
						<li class="tit">반지름</li><li><input type="text" name="chartOption.pie.radius" class="spinner" data-spinner-start="100" data-spinner-step="10" data-default-value="" placeholder="없을경우 자동설정 됩니다."/></li>
						<li class="tit">안쪽반지름</li><li><input type="text" name="chartOption.pie.innerRadius" class="spinner" data-spinner-start="0" data-spinner-end="100" data-spinner-step="10" data-default-value="0" /></li>
						<li class="tit">라벨여백</li><li><input type="text" name="chartOption.pie.labelRadius" class="spinner" data-spinner-start="10" data-spinner-step="10" data-default-value="30" /></li>
						<li class="tit">depth3D</li><li><input type="text" name="chartOption.pie.depth3D" class="spinner" data-spinner-start="0" data-spinner-end="100" data-spinner-step="5" data-default-value="0" /></li>
						<li class="tit">Angle</li><li><input type="text" name="chartOption.pie.angle" class="spinner" data-spinner-start="0" data-spinner-end="60" data-spinner-step="5" data-default-value="0" /></li>
						<li class="tit">투명도</li><li><input type="text" name="chartOption.pie.alpha" class="spinner" data-spinner-start="0.1" data-spinner-end="1" data-spinner-step="0.1" data-default-value="1" /></li>
						<li class="tit">선굵기</li><li><input type="text" name="chartOption.pie.outlineThickness" class="spinner" data-spinner-start="0" data-spinner-end="10" data-spinner-step="1" data-default-value="2" /></li>
					</ul>
				</dd>
			</dl>
			
			<dl class="vis" data-section="bubble" data-chart-use="[6]">
				<dt>버블크기</dt>
				<dd class="calform">
					<input type="text" name="chartOption.xy.minBulletSize" class="spinner w3" data-spinner-step="1" data-default-value="5" placeholder="최소값" />
					~ <input type="text" name="chartOption.xy.maxBulletSize" class="spinner w3" data-spinner-step="1" data-default-value="50" placeholder="최대값" />
				</dd>
			</dl>
		
			<dl class="vis" data-section="scatt" data-chart-use="[7]">
				<dt>포인터크기</dt>
				<dd>
					<input type="text" name="chartOption.xy.bulletSize" class="spinner" data-spinner-step="1" data-default-value="5" />
				</dd>
			</dl>
			
			<dl class="vis" data-section="bubble" data-chart-use="[6,7]">
				<dt>X축 범위</dt>
				<dd class="calform">
					<span class="parse_date_unchecked" data-field="chartOption.categoryAxis.parseDates" data-match="checked" data-match-value="false">
						<input type="text" name="chartOption.xy.xMin" class="spinner w3" data-spinner-step="1" placeholder="최소값" />
						~ <input type="text" name="chartOption.xy.xMax" class="spinner w3" data-spinner-step="1" placeholder="최대값" />
					</span>
					<span class="parse_date_checked" data-field="chartOption.categoryAxis.parseDates" data-match="checked" data-match-value="true">
						<input type="text" name="chartOption.xy.xDateMin" class="datepicker w3" placeholder="최소값" />
						~ <input type="text" name="chartOption.xy.xDateMax" class="datepicker w3" placeholder="최대값" />
					</span>
				</dd>
				<dt>Y축 범위</dt>
				<dd class="calform">
					<input type="text" name="chartOption.xy.yMin" class="spinner w3" data-spinner-step="1" placeholder="최소값" />
					~ <input type="text" name="chartOption.xy.yMax" class="spinner w3" data-spinner-step="1" placeholder="최대값" />
				</dd>
		
				<dt>스타일</dt>
				<dd>
					<ul class="mid stit">
						<li class="tit">투명도</li><li><input type="text" name="chartOption.xy.bulletAlpha" class="spinner" data-spinner-start="0.1" data-spinner-end="1" data-spinner-step="0.1" data-default-value="0.7" /></li>
						<li class="tit">선굵기</li><li><input type="text" name="chartOption.xy.bulletBorderThickness" class="spinner" data-spinner-start="0" data-spinner-end="10" data-spinner-step="1" data-default-value="0" /></li>
					</ul>
				</dd>
			</dl>
			
			<dl class="vis" data-section="bar_rotate" data-chart-use="[1,2,3,4,6,7]">
				<dt>차트 ZOOM</dt>
				<dd>
					<ul class="stit">
						<li class="tit">왼쪽</li>
						<li>
							<span class="check"><input type="checkbox" name="chartOption.valueScrollbar.display" value="Y" id="vs_display" /><label for="vs_display"><span></span>사용</label></span>
							<span class="lbl">위치</span><input type="text" name="chartOption.valueScrollbar.offset" class="spinner w2" data-spinner-start="10" data-spinner-step="5" data-default-value="80" placeholder="Offset" />
						</li>
						<li class="tit">아래</li>
						<li class="check"><input type="checkbox" name="chartOption.chartScrollbar.display" value="Y" id="cs_display" /><label for="cs_display"><span></span>사용</label></li>
					</ul>
				</dd>
			</dl>		
			
			<dl class="vis" data-section="map" data-chart-use="[21]">
				<dt>좌표계</dt>
				<dd>
					<select name="chartOption.map.crs">
						<option value="EPSG:4326">WGS84 경위도</option>
						<option value="EPSG:4019">GRS80 경위도</option>
						<option value="EPSG:3857">Google Mercator</option>
						<option value="EPSG:5185">서부원점(GRS80)</option>
						<option value="EPSG:5186">중부원점(GRS80)</option>
						<option value="EPSG:5182">제주원점(GRS80, 55만)</option>
						<option value="EPSG:5187">동부원점(GRS80)</option>
						<option value="EPSG:5188">동해(울릉)원점(GRS80)</option>
						<option value="EPSG:5179">UTM-K(GRS80)</option>
					</select>
				</dd>
				<dt>지도 종류</dt>
				<dd>
					<select name="chartOption.map.mapType">
						<option value="Base">기본</option>
						<option value="gray">회색지도</option>
						<option value="midnight">야간지도</option>
						<option value="Hybrid">라벨지도</option>
						<option value="none">배경없음</option>
					</select>
				</dd>	
				<dt class="map_option_pnu">지도 경계</dt>
				<dd class="map_option_pnu"> <select name="chartOption.map.boundaryLevel">
						<option value="adsido">시도</option>
						<option value="adsigg">시군구</option>
						<option value="ademd">읍면동</option>
					</select>
					<select name="chartOption.map.boundarySingleCd">
						<option value="">전체시도표시</option>
						<option value="11">서울특별시</option>
						<option value="26">부산광역시</option>
						<option value="27">대구광역시</option>
						<option value="28">인천광역시</option>
						<option value="29">광주광역시</option>
						<option value="30">대전광역시</option>
						<option value="31">울산광역시</option>
						<option value="36">세종특별자치시</option>
						<option value="41">경기도</option>
						<option value="42">강원도</option>
						<option value="43">충청북도</option>
						<option value="44">충청남도</option>
						<option value="45">전라북도</option>
						<option value="46">전라남도</option>
						<option value="47">경상북도</option>
						<option value="48">경상남도</option>
						<option value="50">제주특별자치도</option>
					</select>				
				</dd>
				<dt>라벨</dt>
				<dd>
					<select name="chartOption.map.label" class="ex_selector"><option value=""></option></select><br />
					<input type="checkbox" name="chartOption.map.showLabel" value="Y" id="map_disp_name"><label for="map_disp_name"><span></span>라벨표시</label>
					<input type="checkbox" name="chartOption.map.showValue" value="Y" id="map_disp_val"><label for="map_disp_val" class="ml20"><span></span>값표시</label>
				</dd>			
				<dt class="map_option_pnu map_option_geocode">색상범위(0~100%)</dt>
				<dd class="calform map_option_pnu map_option_geocode"> 
					상위 <input type="text" name="chartOption.map.style.levels[0]" class="spinner"  data-spinner-suffix=" %" data-spinner-start="0" data-spinner-end="100" data-spinner-step="5" data-default-value="20 %" /> 
					&nbsp; &nbsp; &nbsp; &nbsp;<input type="text" class="geo_color_choice" name="chartOption.map.style.colors[0]" data-default-value="#bc001f" /><br />
					상위 <input type="text" name="chartOption.map.style.levels[1]" class="spinner" data-spinner-suffix=" %" data-spinner-start="0" data-spinner-end="100" data-spinner-step="5" data-default-value="40 %" /> 
					&nbsp; &nbsp; &nbsp; &nbsp;<input type="text" class="geo_color_choice" name="chartOption.map.style.colors[1]" data-default-value="#bc5d00" /><br />
					상위 <input type="text" name="chartOption.map.style.levels[2]" class="spinner" data-spinner-suffix=" %" data-spinner-start="0" data-spinner-end="100" data-spinner-step="5" data-default-value="60 %" />
					&nbsp; &nbsp; &nbsp; &nbsp;<input type="text" class="geo_color_choice" name="chartOption.map.style.colors[2]" data-default-value="#efba00" /><br />
					상위 <input type="text" name="chartOption.map.style.levels[3]" class="spinner" data-spinner-suffix=" %" data-spinner-start="0" data-spinner-end="100" data-spinner-step="5" data-default-value="80 %" /> 
					&nbsp; &nbsp; &nbsp; &nbsp;<input type="text" class="geo_color_choice" name="chartOption.map.style.colors[3]" data-default-value="#668021" /><br />
					상위 <input type="text" name="chartOption.map.style.levels[4]" class="spinner" data-spinner-suffix=" %" data-spinner-start="0" data-spinner-end="100" data-spinner-step="5" data-default-value="100 %" /> 
					&nbsp; &nbsp; &nbsp; &nbsp;<input type="text" class="geo_color_choice" name="chartOption.map.style.colors[4]" data-default-value="#2b879b" />
				</dd>
				<dt class="map_option_geocode">영역(원)크기</dt>
				<dd class="calform map_option_geocode">
					<input type="text" name="chartOption.map.radiusMin" class="spinner w3" data-spinner-step="1" placeholder="최소값" data-default-value="5" />
					~ <input type="text" name="chartOption.map.radiusMax" class="spinner w3" data-spinner-step="1" placeholder="최대값" data-default-value="50" />
				</dd>	
				<dt class="map_option_geogroup">포인트크기</dt>
				<dd class="map_option_geogroup">
					<input type="text" name="chartOption.map.radius" class="spinner" data-spinner-start="0" data-spinner-end="20" data-spinner-step="1" data-default-value="5" />
				</dd>			
				<dt>스타일</dt>
				<dd>
					<ul class="mid stit">
						<li class="tit">투명도</li><li><input type="text" name="chartOption.map.style.opacity" class="spinner" data-spinner-start="0" data-spinner-end="1" data-spinner-step="0.1" data-default-value="0.3" /></li>
						<li class="tit">선굵기</li><li><input type="text" name="chartOption.map.style.stroke" class="spinner" data-spinner-start="0" data-spinner-end="10" data-spinner-step="1" data-default-value="2" /></li>
					</ul>
				</dd>	
				<dt class="map_option_pnu">값이없는 지역표시</dt>
				<dd class="map_option_pnu">
					<input type="checkbox" name="chartOption.map.nullBoundary" value="Y" id="map_nul_boun"><label for="map_nul_boun"><span></span>표시하지 않음</label>
				</dd>	
			</dl>	
		</div>	
	
	</div>
	
	<div class="tac mt30">
		<input id="visualize_set_btn" type="button" value="시각화적용" class="btn grey normal_input"/>
		<input id="visualize_save_btn" type="button" value="시각화저장" class="btn red normal_input"/>
	</div>
	
</div>
<!-- E : visualization -->






<div id="dialog_data_select" title="데이터선택">	
	<ul class="tab01 column2">
		<li><a href="#" data-tab="1" class="active">검색</a></li>
		<li><a href="#" data-tab="2">파일업로드</a></li>
	</ul>	
	<div class="dialog_body mt15">
	
		<div id="dialog_data_search">
		<form>
			<input type="text" name="search" value="" placeholder="검색어를 입력하세요." />
			<select name="datasetTab">				
				<option value="BDS">빅데이터셋</option>
				<option value="STA">통계데이터셋</option>
				<option value="PRI">민간데이터셋</option>
				<option value="MGE">결합데이터셋</option>
			</select>
			
			<input type="submit" name="" value="검색" class="btn red"/>
		</form>
			
			<div id="dialog_data_list" class="mt15">
				<script id="tmpl_dialog_data_list" type="text/x-jquery-tmpl">
				<ul>					
					{{each dataList}}
					<li>						
						<span class="dataListChoice_txt">{{= dlName}} {{= dsName}} {{if dlType == "STA"}} <select id="prdDe">
							{{each prdDe}}
							<option value="{{= prdDe}}">{{= prdDe}}</option>
							{{/each}}							
						</select>{{/if}}</span> <span class="dataListChoice" data-tab="{{= dlType}}" data-id="{{= dsId}}" data-title="{{= dsName}}">선택</span>
					</li>
					{{/each}}
					{{if dataList.length <= 0}}
					<li class="nodata">검색 결과가 없습니다.</li>
					{{/if}}
				</ul>
				<c:import url="/WEB-INF/jsp/layout/pages_tmpl.jsp" />
				</script>
			</div>
		</div>

		
		<div id="dialog_data_upload">
			<form action="../dataset/upload.do" method="post" enctype="multipart/form-data">
				<!-- 파일 선택<input type="file" name="file" />
				<input type="submit" value="업로드" /> -->
				<div class="pb10">※엑셀,CSV 파일 업로드시 첫번째 행에 헤더컬럼이 반드시 포함되어 있어야 합니다.</div>
				<div class="file_input_route w84p">
					<input type="hidden" name="target" value="VISUAL" />
					<input type="text" readonly="readonly" id="file_route">
					<label>파일 선택<input type="file" name="cleanwordfile" onchange="javascript:document.getElementById('file_route').value = this.value.split('\\')[this.value.split('\\').length-1]"></label>
				</div>				
				<input type="submit" value="업로드" class="btn red w15p"/>
			</form>
		</div>

		<div id="dialog_data_detail" class="databox h200"></div>

	</div>
</div>





<script type="text/javascript">
var selectSheetNum = "",
	selectSheet = null,
	sheetData = {a:[], b:[], c:[], a1:[], b1:[], c1:[]},
	sheetHeader = {a:[], b:[], c:[], a1:[], b1:[], c1:[]},
	selectDataset = {a:{}, b:{}},
	dialogSheet = null,
	filterPointer = [],
	filterIsReplace = false;

$(function() {
	// 테이블 생성	
	var selectSheetOption = {
		language: "ko-KR",
		search: true,
		rowHeaders: true,
		rowHeights: 30,
		colHeaders: true,
		contextMenu: true,
		selectionMode: 'range',
		renderAllRows: false,
		viewportColumnRenderingOffset : 20,
		viewportRowRenderingOffset : 30,
		outsideClickDeselects: false,
		autoColumnSize: false,
		autoRowSize:false,		
		manualColumnResize: true,
		fixedRowsTop:1,
		fixedColumnsLeft:1,
		afterCreateCol : function () {
			sheetHeader[selectSheetNum] = selectSheet.getDataAtRow(0);
		},
		afterRemoveCol : function() {
			sheetHeader[selectSheetNum] = selectSheet.getDataAtRow(0);
		},
		//afterCreateRow : function (index) {
		//	if(index == 0) sheetHeader[selectSheetNum] = selectSheet.getDataAtRow(0);
		//},
		//afterRemoveRow : function(index) {
		//	if(index == 0) sheetHeader[selectSheetNum] = selectSheet.getDataAtRow(0);
		//},
		afterUndo : function() {
			if(selectSheet.countCols() != sheetHeader[selectSheetNum]) sheetHeader[selectSheetNum] = selectSheet.getDataAtRow(0);
		},
		afterRedo : function() {
			if(selectSheet.countCols() != sheetHeader[selectSheetNum]) sheetHeader[selectSheetNum] = selectSheet.getDataAtRow(0);
		},
		afterChange : function(changes) {
			if(changes != null) 
				changes.forEach(function(param) {
					if(param.row == 0) {
						sheetHeader[selectSheetNum] = selectSheet.getDataAtRow(0);
						return false;
					}
				});
		}
	};	
	selectSheet = new Handsontable($("#data_sheet_table")[0], selectSheetOption);
	
	// TEST
	sheetData["a"] = [["","","","",""]];
	selectSheetNum = "a";
	selectSheet.loadData(sheetData[selectSheetNum]);
	sheetHeader[selectSheetNum] = sheetData[selectSheetNum][0];
	loadSelectedDataAfter();
	
	//-------------------------------------------------------------------------
	// 데이터 선택
	
	// 데이터 선택 창
	dialogDataSearchReset();
	$("#dialog_data_select").dialog({
		autoOpen: false,
		resizable: false,
		height: "auto",
		width: 700,
		modal: true,
		buttons: {
			"확인": function() {
				if(selectedData() !== false) {
					$(this).dialog("close");
				}
			},
			"취소": function() {
				$(this).dialog("close");
			}
		},
		close : function() {
			dialogDataSearchReset();
		}
	});
	
	// 데이터 선택 창 오픈
	$("#btn_data_select").click(function() {
		if($(this).is(".disabled")) return false;
		
        $("#dialog_data_select").dialog("open");
        
    	dialogSheet = new Handsontable($("#dialog_data_detail")[0], {
    		language: "ko-KR",
			rowHeaders: true,
			colHeaders: true,
			readOnly : true,
			fixedRowsTop:1,
			fixedColumnsLeft:1
    	});

        return false;
	});
	
	// 데이터 선택 창 내 검색/업로드 등 선택
	$("#dialog_data_select").find(".tab01 > li > a").click(function() {
		$("#dialog_data_select").find(".tab01 > li > a").removeClass("active");
		var tab = $(this).data("tab");
		$("#dialog_data_select").find(".tab01 > li > a").each(function(i){
			if($(this).data("tab") == tab) {
				$(this).addClass("active");
			}
		});
		$("#dialog_data_select").find(".dialog_body > div").hide();
		$("#dialog_data_select").find(".dialog_body > div:eq(" + ($(this).data("tab") - 1) + ")").show();
		$("#dialog_data_detail").show();
		return false;
	});
	
	// 데이터 검색
	$("#dialog_data_search").find("[type=submit]").click(function() {
		var keyword = $("#dialog_data_search").find("input[name=search]").val();
		var datasetTab = $("#dialog_data_search").find("select[name=datasetTab]").val();		
		searchData(1, keyword, datasetTab);
		return false;
	});
	
	// 데이터 업로드
	$("#dialog_data_upload").find("form").ajaxForm({
		url : "../dataset/upload.do",
		method : "post",
		beforeSubmit: function (data, $form, option) {
			if($.trim($form.find("input[type=file]").val()) == "") {
				alert("파일을 선택해 주세요.");
				return false;
			}
        },
        success: function(json){
        	if(json.result == "success") {
        		$("#dialog_data_upload").data({tab: json.datasetTab, type: json.dataType, id: json.dataId, name: json.fileName});
        		dialogSheet.loadData(json.dataList);
        	} else {
        		alert("데이터를 읽지 못하였습니다.");
        		$("#dialog_data_upload").data({tab: "", type: "", id: "", name: ""});
        		dialogSheet.loadData([]);
        	}        	
        },
        error: function(error){
        	alert("데이터 전송 중 오류가 발생하였습니다.");
    		$("#dialog_data_upload").data({tab: "", type: "", id: "", name: ""});
    		dialogSheet.loadData([]);
        }
    });	
		
	
	
	//-------------------------------------------------------------------------
	// 시각화
		
	// 차트 유형 선택
	$("#select_ctype > li > :radio").click(function() {
		$("#select_ctype").trigger("reset");
	});	
	// 리셋
	$("#select_ctype").bind("reset", function(e) {
		$("#x_header").empty();
		$("#y_header").empty();
		$("#z_header").empty();
		$("#x_selector").empty();
		$("#y_selector").empty();
		$("#z_selector").empty();
		
		$("input[name=visChartX]", "#visFrm").val("");
		$("input[name=visChartY]", "#visFrm").val("");
		$("input[name=visChartZ]", "#visFrm").val("");
		
		$("input[name=visChartXLabel]", "#visFrm").val("");
		$("input[name=visChartYLabel]", "#visFrm").val("");
		$("input[name=visChartZLabel]", "#visFrm").val("");
		
		$("#chartdiv").empty().addClass("vz_empty");
		
		var chosenOption = {
			placeholder_text : "컬럼을 선택하세요.",
		   	no_results_text : "입력하신 컬럼을 찾을 수 없습니다."
		};
		
		var obj = $("#select_ctype > li > :radio:checked");
		var chartType = $(obj).val();
		
		if(sheetHeader[selectSheetNum] == null || sheetHeader[selectSheetNum].length <= 0) return false;

		var columns = sheetHeader[selectSheetNum];
		if(columns.length < 0) return false;
		
		var options = "";
		for(var i=0; i < columns.length; i++) {
			options += '<option value="' + i + '">' + $.trim(columns[i]) + '</option>';
		}		

		if(chartType == 21) {
			$("#geo_header").show();
			$("#geo_selector").show();
			var geoSel = $("#geo_selector > select").val();
			
			if(geoSel == "geocode") {
				$(obj).data("x-header", "*위치기준(위도)");
				$(obj).data("y-header", "*위치기준(경도)");
				$(obj).data("z-header", "*값 기준");
			} else if(geoSel == "geogroup") {
				$(obj).data("x-header", "*위치기준(위도)");
				$(obj).data("y-header", "*위치기준(경도)");
				$(obj).data("z-header", "*클러스터");
			} else {
				$(obj).data("x-header", "*지역기준(지역코드)");
				$(obj).data("y-header", "");
				$(obj).data("z-header", "*값 기준");
			}
			
			$(".map_option_pnu, .map_option_geocode, .map_option_geogroup").hide();
			$(".map_option_" + geoSel).show();
		} else {
			$("#geo_header").hide();
			$("#geo_selector").hide();
		}		

		if($.trim($(obj).data("x-header")) != "") {
			$("#x_header").show();
			$("#x_selector").show();	
			
			var xSelector = $('<select style="width:100%;"></select>'); 
			$("#x_header").text($(obj).data("x-header"));
			if($(obj).data("x-multi")) {
				xSelector.attr("multiple", "multiple");
			} 
			xSelector.append(options);
			$("#x_selector").append(xSelector);
			xSelector.chosen(chosenOption).change(function(e, p) {
				if("selected" in p) $(this).find("option[value=" + p.selected + "]").prop("selected", true);
				if("deselected" in p) $(this).find("option[value=" + p.deselected + "]").prop("selected", false);
			}).addClass("chosen");
		} else {
			$("#x_header").hide();
			$("#x_selector").hide();
		}
		
		if($.trim($(obj).data("y-header")) != "") {
			$("#y_header").show();
			$("#y_selector").show();
			
			var ySelector = $('<select style="width:100%;"></select>');
			$("#y_header").text($(obj).data("y-header"));
			if($(obj).data("y-multi")) {
				ySelector.attr("multiple", "multiple");
			} 
			ySelector.append(options);
			$("#y_selector").append(ySelector);
			ySelector.chosen(chosenOption).change(function(e, p) {
				if("selected" in p) {
					$(this).find("option[value=" + p.selected + "]").prop("selected", true);
					
					// 시각화 옵션
					var html = $(this).find("option[value=" + p.selected + "]").outerHtml();
					$("#vis_option dl:visible select.selected_y").append(html).trigger("chosen:updated");
				}
				if("deselected" in p) {
					$(this).find("option[value=" + p.deselected + "]").prop("selected", false);
					
					// 시각화옵션
					$("#vis_option dl:visible select.selected_y").find("option[value=" + p.deselected + "]").remove().end().trigger("chosen:updated");
				}		
			}).addClass("chosen");
		} else {
			$("#y_header").hide();
			$("#y_selector").hide();
		}
		
		if($.trim($(obj).data("z-header")) != "") {
			$("#z_header").show();
			$("#z_selector").show();
			
			var zSelector = $('<select style="width:100%;"></select>'); 
			$("#z_header").text($(obj).data("z-header"));
			if($(obj).data("z-multi")) {
				zSelector.attr("multiple", "multiple");
			} else if($(obj).data("z-null")) {
				chosenOption.allow_single_deselect = true;
				zSelector.append('<option value=""></option>');
			}	
			zSelector.append(options);
			
			$("#z_selector").append(zSelector);
			zSelector.chosen(chosenOption).change(function(e, p) {
				if("selected" in p) $(this).find("option[value=" + p.selected + "]").prop("selected", true);
				if("deselected" in p) $(this).find("option[value=" + p.deselected + "]").prop("selected", false);
			}).addClass("chosen");
		} else {
			$("#z_header").hide();
			$("#z_selector").hide();
		}
		
		// 시각화별 변수 세팅
		setChartOption(chartType);
		$("#vis_option dl:visible select.ex_selector").empty().append('<option value=""></option>').append(options).trigger("chosen:updated");		
	});	

	
	// 시각화 옵션 필드 초기화
	$("#vis_option input.spinner").each(function() {
		$(this).suf_spinner({
		    step: $(this).data("spinner-step"),
		    max: $(this).data("spinner-end"),
		    min: $(this).data("spinner-start"),
		    suffix: $(this).data("spinner-suffix")
		});		
		
		var cls = $(this).attr("class");
		cls = cls.replace("ui-spinner-input", "");
		cls = cls.replace("spinner", "");
		
		$(this).parent().addClass($.trim(cls));
	});
	
	$("#vis_option dl[data-section=ylabel_multi] select.selected_y").chosen({
		placeholder_text : "Y값 기준을 선택하세요.",
	   	no_results_text : "입력하신 컬럼을 찾을 수 없습니다."
	}).change(function(e, p) {
		if("selected" in p) {
			$(this).find("option[value=" + p.selected + "]").prop("selected", true);
			$("#vis_option dl[data-section=ylabel_multi] select.selected_y").not(this).find("option[value=" + p.selected + "]").prop("disabled", true);
		}
		if("deselected" in p) {
			$(this).find("option[value=" + p.deselected + "]").prop("selected", false);
			$("#vis_option dl[data-section=ylabel_multi] select.selected_y").not(this).find("option[value=" + p.deselected + "]").prop("disabled", false);
		}
		
		$("#vis_option dl[data-section=ylabel_multi] select.selected_y").not(this).trigger("chosen:updated");
	}).addClass("chosen");
	
	$("#vis_option dl:not([data-section=ylabel_multi]) select.selected_y").chosen({
		placeholder_text : "Y값 기준을 선택하세요.",
	   	no_results_text : "입력하신 컬럼을 찾을 수 없습니다."
	}).change(function(e, p) {
		if("selected" in p) $(this).find("option[value=" + p.selected + "]").prop("selected", true);
		if("deselected" in p) $(this).find("option[value=" + p.deselected + "]").prop("selected", false);
	}).addClass("chosen");
	
	$("#vis_option dl select.ex_selector").chosen({
		placeholder_text : "컬럼을 선택하세요.",
	   	no_results_text : "입력하신 컬럼을 찾을 수 없습니다.",
	   	allow_single_deselect : true
	}).change(function(e, p) {
		if("selected" in p) $(this).find("option[value=" + p.selected + "]").prop("selected", true);
		if("deselected" in p) $(this).find("option[value=" + p.deselected + "]").prop("selected", false);
	}).addClass("chosen");
	
	// X축 시계열 선택시 
	$("#parse_date").click(function() {
		if($(this).is(":checked")) {
			$(".parse_date_checked").show();
			$(".parse_date_unchecked").hide();
		} else {
			$(".parse_date_checked").hide();
			$(".parse_date_unchecked").show();
		}
	});	
	
	// 시각화 적용
	$("#visualize_set_btn").click(function() {
		var chartType = $("#select_ctype > li > :radio:checked").val();
		var sheet = sheetData[selectSheetNum];
		var header = sheetHeader[selectSheetNum];

		// chartType, dataSheet, col.x, col.y, col.z, prop
		var visOptions = visFieldOptions(header);

		// 필드 선택 확인
		if(!checkColumnSelect()) {
			return false;
		}
		
		$("#chartdiv").removeClass("vz_empty");
		visCreateChart(chartType, sheet, visOptions);
	});
	
	// 시각화 컬러선택
	$("#color_choice").spectrum({
		allowEmpty:true,
		disabled : true,
		replacerClassName: 'spectrum_hidden',
		cancelText: "취소",
	    chooseText: "선택",
	    beforeShow: function(color) {
	    	$(this).spectrum("set", null);
	    },
		hide: function(color) {
			if(color == null) return;
			appendVisColor("#color_palette", color.toHexString());
		}
	});
	$("#color_choice_btn").click(function() {
	    $("#color_choice").spectrum("toggle");
	    return false;
	});
	$("#vis_option dl[data-section=colortheme] input[type=radio]").click(function() {
		if($(this).val() == "patterns") {
			$("#color_choice_btn").hide();
			
			$("#color_palette").children("li:not(:last)").remove();
			getVisColorListThemePattern("#color_palette");
		} else {
			$("#color_choice_btn").show();
			
			$("#color_palette").children("li:not(:last)").remove();
			getVisColorListTheme("#color_palette", $(this).val());
		}
	});
	
	$("#geo_selector > select").change(function() {
		$("#v9").click();
	});
	$(".geo_color_choice").spectrum({
		allowEmpty:true,
		cancelText: "취소",
	    chooseText: "선택"
	});	
	
	
	// 저장 버튼
	$("#visualize_save_btn").click(function() {
		// 체크
		if($.trim($("[name=visTitle]", "#visFrm").val()) == "") {
			alert("시각화 제목을 입력해주세요.");
			return false;
		}
		if($.trim($("[name=visCate1]", "#visFrm").val()) == "" || $.trim($("[name=visCate2]", "#visFrm").val()) == "") {
			alert("시각화 분류를 선택해주세요.");
			return false;
		}
		
		if($("input[name=visChartX]", "#visFrm").val() == ""
			&& $("input[name=visChartY]", "#visFrm").val() == ""
			&& $("input[name=visChartZ]", "#visFrm").val() == "") {
				alert("시각화 적용 후 저장 할 수 있습니다.");
				return false;
		}
			
		// 필드 선택 확인
		if(!checkColumnSelect()) {
			return false;
		}

		// 카테고리 선택
		var chartType = $("#select_ctype > li > :radio:checked").val();
		var chartName= $("#select_ctype > li > :radio:checked").data("cname");
		var sheet = sheetData[selectSheetNum];
		var header = sheetHeader[selectSheetNum];

		// chartType, dataSheet, col.x, col.y, col.z, prop
		var visOptions = visFieldOptions(header);
		var formdata = $("#visFrm").serializeObject();
				
		formdata.visChartType = chartType;
		formdata.visChartName = chartName;
		
		delete visOptions._columns;
		delete visOptions._labels;
		delete visOptions._chartId;
		delete visOptions._mapId;

		formdata.visChartProperty = JSON.stringify(visOptions);
		formdata.sheetdata = JSON.stringify(sheetData[selectSheetNum]);

		// 섬네일을 생성
		visCreateThumbnail(function(data) {
			formdata.thumbnail = data;

			$.postJSON($("#visFrm").attr("action"), formdata, function(json) {
				if(json.result == "success") {
					document.location.href = "./list.do";
				} else {
					alert(json.message);
				}
			});
		});
		
		return false;
	});
	
	$("#select_ctype").trigger("reset");
});

// 차트종류에 따른 옵션 설정
function setChartOption(chartType) {
	chartType = +chartType;
	$("#vis_option > dl").each(function() {
		var used = $(this).data("chart-use");		
		if($.inArray(chartType, used) > -1) {
			$(this).show();
		} else {
			$(this).hide();
		}
	});	
	
	$("#vis_option input[type=text]").each(function() {
		$(this).val($(this).data("default-value"));
	});
	
	$("#vis_option input[type=checkbox], #vis_option input[type=radio]").each(function() {
		$(this).prop("checked", $(this).data("default-checked") == true);
	});
	
	$("#vis_option option[data-default-selected=true]").prop("selected", true);
	
	$("#vis_option select.selected_y").empty().trigger("chosen:updated");
	
	$("#vis_option select.ex_selector").empty().trigger("chosen:updated");
	
	// 시계열 선택 초기화
	$(".parse_date_checked").hide();
	$(".parse_date_unchecked").show();
	
	// 기타 차트별 설정
	if(chartType == 3) {
		$("#vis_option input[name='chartOption.graph.bulletSize']").val(0);
	}	
	
	if(chartType == 8) {
		$("#vis_option dl[data-section=colortheme] label:eq(1)").hide();
	} else {
		$("#vis_option dl[data-section=colortheme] label").show();
	}	
	if(chartType == 21 && $("#geo_selector > select").val() == "geogroup") {
		$("#vis_option dl[data-section=colortheme]").show();
		$("#vis_option dl[data-section=colortheme] label:eq(1)").hide();
	}
	// 컬러셋 설정
	$("#vis_option dl[data-section=colortheme] input[type=radio]:eq(0)").trigger("click");
	$(".geo_color_choice").each(function () {
		$(this).spectrum("set", $(this).data("default-value"));
	});
}

function checkColumnSelect() {
	// 필드 선택 확인
	var obj = $("#select_ctype > li > :radio:checked");
	if($.trim(obj.data("x-header")) != "" && obj.data("x-null") != true
			&& $("input[name=visChartX]", "#visFrm").val() == "") {
		alert("시각화 옵션의 " + obj.data("x-header").replace("*", "") + " 컬럼을" + (obj.data("x-multi") === true ? " 하나이상" : "") + " 선택해주세요.");
		return false;
	}
	if($.trim(obj.data("y-header")) != "" && obj.data("y-null") != true
			&& $("input[name=visChartY]", "#visFrm").val() == "") {
		alert("시각화 옵션의 " + obj.data("y-header").replace("*", "") + " 컬럼을" + (obj.data("y-multi") === true ? " 하나이상" : "") + " 선택해주세요.");
		return false;
	}
	if($.trim(obj.data("z-header")) != "" && obj.data("z-null") != true
			&& $("input[name=visChartZ]", "#visFrm").val() == "") {
		alert("시각화 옵션의 " + obj.data("z-header").replace("*", "") + " 컬럼을" + (obj.data("z-multi") === true ? " 하나이상" : "") + " 선택해주세요.");
		return false;
	}
	
	return true;
}

//시트에 데이터 세팅
function setSheetData(data) {
	if(data == null || data.length <= 0 || data[0] == null || data[0].length <= 0) {
		data = null;
	}
	selectSheet.loadData(data);
	loadSelectedDataAfter();
	
	// 선택한 값들 reset
	$("#select_ctype").trigger("reset");
}

//데이터 불러오기 창 리셋 
function dialogDataSearchReset() {
	$("#dialog_data_search").find("form")[0].reset();
	$("#dialog_data_list").empty().append($("#tmpl_dialog_data_list").tmpl({dataList : []}));
	$("#dialog_data_detail").empty();
		
	$("#dialog_data_upload").find("form")[0].reset();
	$("#dialog_data_upload").data({tab: "", type: "", id: ""});
	
	$("#dialog_data_select").find(".dialog_body > div").hide().filter(":first").show();
	$("#dialog_data_detail").show();
	
	if(dialogSheet) dialogSheet.destroy();
	dialogSheet = null;
}

//데이터 검색
function searchData(pageIndex, keyword, datasetTab) {
	if($.trim(keyword) == "") {
		alert("검색 키워드를 입력하세요.");
		return false;
	}

	$.getJSON("../dataset/search.do", {keyword: keyword, datasetTab: datasetTab, pageIndex: pageIndex}, function(json) {
		
		$("#dialog_data_list").empty().append($("#tmpl_dialog_data_list").tmpl(json));

		$("#dialog_data_list > ul span.dataListChoice").click(function() {
			$("#dialog_data_list > ul span.dataListChoice").removeClass("active");
			$(this).addClass("active");

			if(datasetTab == "STA") {
				var prdDe = $(this).parent("li").find("select#prdDe").val();
				loadSelectedStaDataSample($(this).data("tab"), $(this).data("id"), prdDe);
			} else {
				loadSelectedDataSample($(this).data("tab"), $(this).data("id"));
			}

			return false;
		});
		// 페이징
		$("#dialog_data_list .paging a").click(function() {
			searchData($(this).data("page"), keyword, datasetTab);
			return false;
		});
	});
}

// 데이터 선택시(확인 버튼 클릭시 데이터 로드)
function selectedData() {
	if($("#dialog_data_search").is(":visible")) {
		var datasetTab = $("#dialog_data_list > ul span.active").data("tab");
		var dataId = $("#dialog_data_list > ul span.active").data("id");
		// 통계인경우
		var prdDe = $("#dialog_data_list > ul span.active").parent("li").find("select#prdDe").val();
		prdDeText = prdDe;
		dataIdVal = dataId;

		selectDataset[selectSheetNum] = {datasetTab:datasetTab, dataId:dataId, prdDe:prdDe};
		
		$("#ana_data_name").val($("#dialog_data_list > ul span.active").data("title"));
	} else {		
		var dataParentId = $("#dialog_data_upload").data("tab");
		var dataId = $("#dialog_data_upload").data("id");
		var dataType = $("#dialog_data_upload").data("type");

		selectDataset[selectSheetNum] = {datasetTab:"UP", dataId:dataId, dataParentId:dataParentId, dataType:dataType};
		
		$("#ana_data_name").val($("#dialog_data_upload").data("title"));
	}
	
	return loadSelectedData(1);
}
// 선택된 데이터 로드
function loadSelectedData(pageIndex) {	
	var datasetTab = selectDataset[selectSheetNum].datasetTab;
	var dataId = selectDataset[selectSheetNum].dataId;
	var prdDe = selectDataset[selectSheetNum].prdDe;

	// 파일업로드일경우
	if(datasetTab == "UP") {
		var dataParentId = selectDataset[selectSheetNum].dataParentId;
		var dataType = selectDataset[selectSheetNum].dataType;
		
		if(dataId == "") {
			alert("업로드된 데이터가 없습니다.");
			return false;
		}

		$.postJSON("../dataset/rawdataUploaded.do", {datasetTab:dataParentId, dataId:dataId, dataType:dataType}, function(json) {
			sheetData[selectSheetNum] = json.dataList;
			sheetHeader[selectSheetNum] = json.dataList.length > 0 ? json.dataList[0] : [];
			
			setSheetData(sheetData[selectSheetNum]);
		});
	} else {		
		if(dataId == "") {
			alert("데이터를 선택해 주세요.");
			return false;
		}
		
		$.postJSON("../dataset/rawdata.do", {datasetTab:datasetTab, dataId:dataId, prdDe:prdDe, limit:100}, function(json) {
		//console.log(json.dataList);
			sheetData[selectSheetNum] = json.dataList;
			sheetHeader[selectSheetNum] = json.dataList.length > 0 ? json.dataList[0] : [];
			
			setSheetData(sheetData[selectSheetNum]);
		});	
	}
}

// 데이터 로드 후 컬럼 사이즈 맞춤
// 자동맞춤일경우 랜더링시간이 많이 걸려 최초 데이터 로드시에만 실행하도록 변경
function loadSelectedDataAfter() {
	var plugin = selectSheet.getPlugin('autoColumnSize');
	plugin.calculateColumnsWidth(void 0, void 0, true);
	var colWidths = [];
	for (var i = 0;i < selectSheet.countCols(); i++) {
		colWidths.push(plugin.getColumnWidth(i));
	}
	selectSheet.updateSettings({colWidths: colWidths});
}

// 데이터 샘플 불러오기(최대 100건)
function loadSelectedDataSample(datasetTab, dataId) {
	$.postJSON("../dataset/rawdata.do", {datasetTab:datasetTab, dataId:dataId, limit: 100}, function(json) {
		dialogSheet.loadData(json.dataList);
	});
}

// 통계 데이터 샘플 불러오기
function loadSelectedStaDataSample(datasetTab, dataId, prdDe) {
	$.postJSON("../dataset/rawdata.do", {datasetTab:datasetTab, dataId:dataId, prdDe:prdDe}, function(json) {
		dialogSheet.loadData(json.dataList);
	});
}
</script>