<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script src="<c:url value='/js/bigdata/portal/svc/retnFmlg/modal/commonModal.js'/>" defer></script>
<script src="<c:url value='/js/bigdata/portal/svc/retnFmlg/modal/retnFmlgModal.js'/>" defer></script>

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
			             	<button type="button" class="srchAddr layPopBtn w20p" data-target="addrModal" data-addrPrefix="mvt"><span>검색하기</span></button>
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
		             	<button type="button" class="srchAddr layPopBtn w20p" data-target="addrModal" data-addrPrefix="hope"><span>검색하기</span></button>
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
	             		<button class="srchCtvt layPopBtn w20p" type="button" data-target="hopeCtvtModal"><span>검색하기</span></button>
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