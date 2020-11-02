<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script src="<c:url value='/js/bigdata/portal/svc/retnFmlg/modal/commonModal.js'/>" defer></script>
<script src="<c:url value='/js/bigdata/portal/svc/retnFmlg/modal/retnFmlgModal.js'/>" defer></script>

<style>
table{border-top:1px solid #252525;margin-bottom:16px;color:#212529;border-collapse:collapse;border-spacing:0;border:1px;}
caption{position: static;top: -10000px;left: -10000px;height: 1px;width: 1px;text-align: left;overflow: hidden;}
th{text-align:inherit}
tr{border:1px;}
table td,table th{padding:15px 10px;line-height:1.42857143;vertical-align:top;border-bottom:1px solid #dee2e6;border-left: 1px solid #dee2e6;}
table th:first-child,table td:first-child{border-left:0;}
table thead th{text-align:center;border-bottom:1px solid #bbb}
.txt-center{text-align:center;}
.txt-right{text-align:right;}
.txt-left{text-align:left;}
</style>

<!-- 주소 검색 모달 -->
<div id="addrModal" class="layPop">
    <div class="popWrp">
        <div class="header">
            <h3>주소입력</h3>
            <button type="button" class="btnClose"><span>닫기</span></button>
        </div>
        <div class="body">
            <div class="formBox">
            	<div class="rowWrap">
	                <div class="row">
	                    <input type="text" class="w100p">
	                </div>
	            </div>

            </div>
        </div>
        <div class="paging">
        	<div class="wrap_page">
        		<a class="first" href="#"><em>처음으로 이동</em></a>
        		<a class="pre" href="#"><em>이전 페이지 이동</em></a>
        		<span class="num">
        		</span>
        		<a class="next" href="#"><em>다음 페이지 이동</em></a>
        		<a class="last" href="#"><em>마지막 페이지 이동</em></a>
        	</div>
        </div>
        <div class="footer">
            <button id="setAddrBtn" type="button"><span>등록하기</span></button>
            <div class="txtR fs11">
	            	<span><a href="http://www.juso.go.kr">www.juso.go.kr</a> API 사용</span>
            </div>
        </div>
    </div>
</div>

<!-- 본인정보 수정하기-->
<div id="updtInfoModal" class="layPop">
    <div class="popWrp">
        <div class="header">
            <h3>본인정보 수정</h3>
            <button type="button" class="btnClose"><span>닫기</span></button>
        </div>
        <div class="body">
            <div class="formBox">
            	<div class="rowWrap">
	                <div class="row">
	                       <input type="text" name="mvtRdNmAdr" class="w80p" placeholder="현거주지 정보를 입력하세요.">
			             	<button id="mvtSrchAddr" type="button" class="srchAddr layPopBtn w20p" data-target="addrModal" data-addrPrefix="mvt"><span>검색하기</span></button>
	                </div>
	                <div class="row">
	                    <p class="w20p">성별</p>
	                    <div class="w80p">
	                        <label><input type="radio" name="selfSexdstn" value='male'><span>남자</span></label>
	                        <label><input type="radio" name="selfSexdstn" value='female'><span>여자</span></label>
	                    </div>
	                </div>
	                <div class="row">
	                    <p class="w20p">나이</p>
	                    <input type="text" class="w80p" name="selfAge">
	                </div>
	            </div>
            </div>
        </div>
        <div class="footer">
            <button type="button" id="updtSelfInfoBtn"><span>수정하기</span></button>
        </div>
    </div>
</div>

<!-- 동거가족 입력 모달 -->
<div id="livtgtModal" class="layPop">
    <div class="popWrp">
        <div class="header">
            <h3>동거가족입력</h3>
            <button type="button" class="btnClose"><span>닫기</span></button>
        </div>
        <div class="body">
            <div class="formBox">
            	<div class="rowWrap">
	                <div class="row">
	                    <p class="w20p">관계</p>
	                    <div class="w80p">
	                        <label><input type="radio" name="relate" value="spouse"><span>배우자</span></label>
	                        <label><input type="radio" name="relate" value="chldrn"><span>자녀</span></label>
	                        <label><input type="radio" name="relate" value="parnts"><span>부모</span></label>
	                        <label><input type="radio" name="relate" value="empty"><span>없음</span></label>
	                    </div>
	                </div>
	                <div class="row">
	                    <p class="w20p">성별</p>
	                    <div class="w80p">
	                        <label><input type="radio" name="relateSexdstn" value="male"><span>남자</span></label>
	                        <label><input type="radio" name="relateSexdstn" value="female"><span>여자</span></label>
	                    </div>
	                </div>
	                <div class="row">
	                    <p class="w20p">나이</p>
	                    <input type="text" class="w80p" name="relateAge">
	                </div>
	            </div>
	         </div>
        </div>
        <div class="footer">
            <button type="button" id="addLivtgt"><span>추가하기</span></button>
        </div>
    </div>
</div>


<!-- 동거가족 수정 모달 -->
<div id="updtLivtgtModal" class="layPop">
    <div class="popWrp">
        <div class="header">
            <h3>동거가족수정</h3>
            <button type="button" class="btnClose"><span>닫기</span></button>
        </div>
        <div class="body">
            <div class="formBox scroll">
            	<div class="rowWrap">
	                <div class="row">
	                    <p class="w20p">관계</p>
	                    <div class="w80p">
	                        <label><input type="radio" name="relate" value="spouse"><span>배우자</span></label>
	                        <label><input type="radio" name="relate" value="chldrn"><span>자녀</span></label>
	                        <label><input type="radio" name="relate" value="parnts"><span>부모</span></label>
	                        <label><input type="radio" name="relate" value="empty"><span>없음</span><label>
	                    </div>
	                </div>
	                <div class="row">
	                    <p class="w20p">성별</p>
	                    <div class="w80p">
	                        <label><input type="radio" name="relateSexdstn" value="male"><span>남자</span></label>
	                        <label><input type="radio" name="relateSexdstn" value="female"><span>여자</span></label>
	                    </div>
	                </div>
	                <div class="row">
	                    <p class="w20p">나이</p>
	                    <input type="text" class="w80p" name="relateAge">
	                </div>
	            </div>
<!-- 	            <button type="button" id="addLivtgtBtn"><span>추가하기</span></button> -->
            </div>
        </div>
        <div class="footer">
            <button type="button" id="updtLivtgtModalBtn"><span>수정하기</span></button>
        </div>
    </div>
</div>


<!-- 희망귀농지역 입력 모달  -->
<!-- <div id="hopeAreaModal" class="layPop"> -->
<!--     <div class="popWrp"> -->
<!--         <div class="header"> -->
<!--             <h3>희망귀농지역입력</h3> -->
<!--             <button type="button" class="btnClose"><span>닫기</span></button> -->
<!--         </div> -->
<!--         <div class="body"> -->
<!--             <div class="formBox"> -->
<!--             	<div class="rowWrap"> -->
<!-- 	                <div class="row"> -->
<!-- 	                    <p class="w20p">시,도</p> -->
<!-- 	                    <div class="w80p"> -->
<!-- 	                        <select> -->
<!-- 	                            <option value="">선택해주세요.</option> -->
<!-- 	                        </select> -->
<!-- 	                    </div> -->
<!-- 	                </div> -->
<!-- 	                <div class="row"> -->
<!-- 	                    <p class="w20p">시,군,구</p> -->
<!-- 	                    <div class="w80p"> -->
<!-- 	                        <select> -->
<!-- 	                            <option value="">시,도를 먼저 선택 후 선택해 주세요.</option> -->
<!-- 	                        </select> -->
<!-- 	                    </div> -->
<!-- 	                </div> -->
<!-- 	                <div class="row"> -->
<!-- 	                    <p class="w20p">읍,면,동</p> -->
<!-- 	                    <div class="w80p"> -->
<!-- 	                        <select> -->
<!-- 	                            <option value="">시,도를 먼저 선택 후 선택해 주세요.</option> -->
<!-- 	                        </select> -->
<!-- 	                    </div> -->
<!-- 	                </div> -->
<!-- 	            </div> -->
<!--             </div> -->
<!--         </div> -->
<!--         <div class="footer"> -->
<!--             <button type="button"><span>등록하기</span></button> -->
<!--         </div> -->
<!--     </div> -->
<!-- </div> -->

<!-- 희망귀농지역 수정 모달  -->
<div id="updtHopeAreaModal" class="layPop">
    <div class="popWrp">
        <div class="header">
            <h3>희망귀농지역수정</h3>
            <button type="button" class="btnClose"><span>닫기</span></button>
        </div>
        <div class="body">
            <div class="formBox">
            	<div class="rowWrap">
	            	<div class="row">
		             	<input type="text" class="w80p" name="hopeRdNmAdr" placeholder="희망 귀농지역을 입력하세요.">
		             	<input type="hidden" class="w80p" name="hopeCtprvn" >
		             	<input type="hidden" class="w80p" name="hopeSigngu" >
		             	<input type="hidden" class="w80p" name="hopeEmd" >
		             	<button id="hopeSrchAddr" type="button" class="srchAddr layPopBtn w20p" data-target="addrModal" data-addrPrefix="hope"><span>검색하기</span></button>
		             </div>
            </div>
         </div>
     </div>
        <div class="footer">
            <button type="button" id="updtHopeAreaModalBtn"><span>수정하기</span></button>
        </div>
    </div>
</div>



<!-- 희망재배품목 입력 모달 -->
<div id="hopeCtvtModal" class="layPop" style="z-index: 300">
    <div class="popWrp">
        <div class="header">
            <h3>희망재배품목입력</h3>
            <button type="button" class="btnClose"><span>닫기</span></button>
        </div>
        <div class="body">
            <div class="formBox">
            	<div class="rowWrap">
	            </div>
	            <div class="paging">
	        	<div class="wrap_page">
	        		<a class="first" href="#"><em>처음으로 이동</em></a>
	        		<a class="pre" href="#"><em>이전 페이지 이동</em></a>
	        		<span class="num">
	        		</span>
	        		<a class="next" href="#"><em>다음 페이지 이동</em></a>
	        		<a class="last" href="#"><em>마지막 페이지 이동</em></a>
	        	</div>
	        </div>
            </div>
        </div>
        <div class="footer">
            <button id="setCtvtBtn" type="button"><span>등록하기</span></button>
        </div>
    </div>
</div>

<!-- 희망재배품목 수정 모달 -->
<div id="updtHopeCtvtModal" class="layPop" >
    <div class="popWrp">
        <div class="header">
            <h3>희망재배품목수정</h3>
            <button type="button" class="btnClose"><span>닫기</span></button>
        </div>
        <div class="body">
            <div class="formBox">
            	<div class="rowWrap">
            		<div class="row">
	             		<input type="text" class="w80p" name="hopeCtvt" placeholder="희망 재배품목을 입력하세요.">
	             		<button id="srchCtvt" class="srchCtvt layPopBtn w20p" type="button" data-target="hopeCtvtModal"><span>검색하기</span></button>
	             	</div>
	            </div>
            </div>
        </div>
        <div class="footer">
            <button id="updtHopeCtvtModalBtn" type="button"><span>수정하기</span></button>
        </div>
    </div>
</div>


<!-- 고려사항 모달 -->
<div id="cnsdrModal" class="layPop">
        <div class="popWrp">
            <div class="header">
                <h3>상위고려사항입력</h3>
                <button type="button" class="btnClose" ><span>닫기</span></button>
            </div>
            <div class="body">
                <div class="formBox">
                	<div class="rowWrap">
	                    <ul>
	                        <li>
	                            <label><input type="checkbox" name="cnsdrCheckBox" value="1"><span>교통시설(터미널/기차역/공항)접근성 우수 지역</span></label>
	                        </li>
	                        <li>
	                            <label><input type="checkbox" name="cnsdrCheckBox" value="2"><span>의료시설(공공/종합/일반의원) 접근성 우수 지역</span></label>
	                        </li>
	                        <li>
	                            <label><input type="checkbox" name="cnsdrCheckBox" value="3"><span>학교시설(초/중/고등학교) 접근성 우수 지역</span></label>
	                        </li>
	                        <li>
	                            <label><input type="checkbox" name="cnsdrCheckBox" value="4"><span>교육시설(학원/교습소) 수 우수 지역</span></label>
	                        </li>
	                    </ul>
	                    <ul>
	                        <li>
	                            <label><input type="checkbox" name="cnsdrCheckBox" value="5"><span>상업시설(대규모점포/전통시장) 접근성 우수 지역</span></label>
	                        </li>
	                        <li>
	                            <label><input type="checkbox" name="cnsdrCheckBox" value="6"><span>편의시설(은행/편의점/극장) 수 우수 지역</span></label>
	                        </li>
	                        <li>
	                            <label><input type="checkbox" name="cnsdrCheckBox" value="7"><span>문화시설 우수 지역</span></label>
	                        </li>
	                    </ul>
	                    <ul>
	                        <li>
	                            <label><input type="checkbox" name="cnsdrCheckBox" value="8"><span>귀농지원정책 우수 지역</span></label>
	                        </li>
	                        <li>
	                            <label><input type="checkbox" name="cnsdrCheckBox" value="9"><span>농산물유통여건건(직매장/유통센터) 우수 지역</span></label>
	                        </li>
	                        <li>
	                            <label><input type="checkbox" name="cnsdrCheckBox" value="10"><span>농지 실거래 가격이 낮은 지역</span></label>
	                        </li>
	                        <li>
	                            <label><input type="checkbox" name="cnsdrCheckBox" value="11"><span>농지 임대 가격이 낮은 지역</span></label>
	                        </li>
	                    </ul>
	                </div>
                </div>
            </div>
            <div class="footer">
                <button type="button" id="setCnsdrBtn"><span>등록하기</span></button>
            </div>
        </div>
    </div>
    
<!-- 데이터 출처 모달 -->
<div id="dataSource" class="layPop" >
    <div class="popDsWrp">
        <div class="header">
            <h3>데이터 출처</h3>
            <button type="button" class="btnClose"><span>닫기</span></button>
        </div>
        <div class="body" style="overflow: scroll; height: 500px">
			<table>
				<caption>테이블명</caption>
				<colgroup>
					<col>
					<col>
					<col>
					<col>
				</colgroup>
				<thead>
					<tr>
						<th scope="col">연번</th>
						<th scope="col">자료명</th>
						<th scope="col">제공기관</th>
						<th scope="col">데이터범위</th>
						<th scope="col">기준년도</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td class="txt-center">1</td>
						<!-- 정렬은 txt-center  중앙정렬, txt-left 좌측정렬, txt-right 우측정렬로 설정하실수있습니다. -->
						<td>농업경영체DB</td>
						<td>농식품부</td>
						<td>o농업인영농정보</td>
						<td>2015~2019</td>
					</tr>
					<tr>
						<td class="txt-center">2</td>
						<td>인구이동통계</td>
						<td>행정안전부</td>
						<td>o전입자및전출자정보</td>
						<td>2015~2019</td>
					</tr>
					<tr>
						<td rowspan="4" class="txt-center">3</td>
						<td rowspan="4" >교통접근성지표</td>
						<td rowspan="4">한국교통연구원</td>
						<td>o교통시설접근성정보</td>
						<td rowspan="4">2018</td>
					</tr>
						<tr><td>o의료시설접근성정보</td></tr>
						<tr><td>o쇼핑시설접근성정보</td></tr>			
						<tr><td>o교육시설접근성정보</td></tr>
					<tr>
						<td class="txt-center">4</td>
						<td>학원 및 교습소 정보</td>
						<td>교육청</td>
						<td>o학원및교습소정보</td>
						<td>2020</td>
					</tr>
					<tr>
						<td rowspan="4" class="txt-center">5</td>
						<td rowspan="4">전국문화기반 시설총람</td>
						<td rowspan="4">문화체육관광부</td>
						<td>o문화의집정보</td>
						<td rowspan="4">2019</td>
					</tr>
						<tr><td>o도서관정보</td></tr>
						<tr><td>o박물관정보</td></tr>
						<tr><td>o지방문화원정보</td></tr>
					</tr>
					<tr>
						<td class="txt-center" rowspan="2">6</td>
						<td rowspan="2">편의위락시설정보</td>
						<td rowspan="2">행정안전부</td>
						<td>o편의점정보</td>
						<td rowspan="2">2020</td>
					</tr>	
						<tr><td>o영상영화관정보</td></tr>
					</tr>
					<tr>
						<td class="txt-center">7</td>
						<td>도로명주소DB</td>
						<td>행정안전부</td>
						<td>o금융기관정보</td>
						<td>2020</td>
					</tr>
					<tr>
						<td class="txt-center">8</td>
						<td>실거래가공개시스템</td>
						<td>국토교통부</td>
						<td>o토지/주택 실거래가정보</td>
						<td>2014~계속</td>
					</tr>
					<tr>
						<td class="txt-center">9</td>
						<td>로컬푸드매장 현황</td>
						<td>aT</td>
						<td>o로컬푸드매장정보</td>
						<td>2020</td>
					</tr>
					<tr>
						<td class="txt-center">10</td>
						<td>APC 현황</td>
						<td>농정원</td>
						<td>oAPC여부 및 취급품목정보</td>
						<td>2020</td>
					</tr>
					<tr>
						<td class="txt-center">11</td>
						<td>귀농지원정책현황</td>
						<td>귀농귀촌종합센터</td>
						<td>o지자체별귀농정책정보</td>
						<td>2020</td>
					</tr>
					<tr>
						<td class="txt-center">12</td>
						<td>도매시장DB</td>
						<td>농정원</td>
						<td>o품목별가격,물동량정보</td>
						<td>2014~2019</td>
					</tr>
					<tr>
						<td class="txt-center">13</td>
						<td>일자리 정보</td>
						<td>워크넷</td>
						<td>o지역일자리정보</td>
						<td>2018~계속</td>
					</tr>
					<tr>
						<td class="txt-center">14</td>
						<td>농지매물 정보</td>
						<td>농어촌공사</td>
						<td>o농지매물정보</td>
						<td>2018~계속</td>
					</tr>
					<tr>
						<td class="txt-center">15</td>
						<td>소득정보</td>
						<td>농촌진흥청</td>
						<td>o주요품목소득정보</td>
						<td>2019</td>
					</tr>
					<tr>
						<td class="txt-center">16</td>
						<td>소매가격정보</td>
						<td>aT</td>
						<td>o주요품목소매가격정보</td>
						<td>2018~계속</td>
					</tr>
					<tr>
						<td class="txt-center">17</td>
						<td>대안학교정보</td>
						<td>교육청</td>
						<td>o대안학교정보</td>
						<td>2020</td>
					</tr>
				</tbody>
			</table>
        </div>
        </br>
        <div class="footer">
            <button id="closeDataSrc" type="button"><span>닫기</span></button>            
        </div>
    </div>
</div>