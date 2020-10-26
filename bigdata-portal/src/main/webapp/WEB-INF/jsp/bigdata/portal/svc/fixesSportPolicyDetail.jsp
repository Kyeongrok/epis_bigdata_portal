<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%--
맞춤 지원 정책
0207
--%>

<!DOCTYPE html>
<html lang="ko">

<head>
    <title></title>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">
    <meta name="HandheldFriendly" content="true">
    <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/resources/svc/fixesSportPolicy/css/reset.css'/>">
    <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/resources/svc/fixesSportPolicy/css/layout.css'/>">

	<script type="text/javascript" src="<c:url value="/js/vendor/jquery/jquery-2.1.4.min.js" />" ></script>
	<script type="text/javascript" src="<c:url value="/js/bigdata/portal/jquery-cs-0.9a.js" />" ></script>
	<script type="text/javascript" src="<c:url value="/js/bigdata/portal/jquery.loading.min.js" />" ></script>
	
	<script type="text/javascript" src="<c:url value='/js/bigdata/portal/svc/fixesSportPolicyDetail.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/bigdata/portal/svc/quick.js'/>"></script>

</head>

<body>
    <div id="skipnavigation"><a href="#sub_content">본문으로 바로가기</a> <a href="">메뉴로 바로가기</a></div>
    <div class="header_area">
        <div class="header_box">
            <h1><a herf="#"><img src="<c:url value='/resources/svc/fixesSportPolicy/img/logo.png'/>" alt="로고"></a></h1>

        </div>
    </div>
    <div id="sub_content" class="sub_cont">
        <div class="sub_top_box">
            <h2>맞춤 <b>지원정책</b> 안내 서비스</h2>
        </div>

        <!--search_form-->
        <div class="sub_sch_area">
            <h3>맞춤 <b>지원정책</b> 안내 서비스에 오신것을 환영합니다.</h3>
            <p>맞춤 <b>지원정책</b> 안내 서비스는 여러분들의 여건에 맞는 지원정책을 안내 해드리는 검색서비스입니다. </p>

            <div class="tab_area">
                <div class="tab_inner">
                    <button class="tablinks" onclick="openCity(event, 'famer')" id="defaultOpen">농업인</button>
                    <button class="tablinks" onclick="openCity(event, 'lowfirm')">법인</button>
                    <button class="tablinks" onclick="openCity(event, 'be_famer')">비농업인</button>
                </div>
                <div id="famer" class="tabcontent">
                    <div class="sch_form">
                        <label for="" class="hdn">검색어입력</label>
                        <input type="text" placeholder="농업경영체 등록번호 또는 원하는 검색 키워드를 입력하세요.">

                        <div class="sch_btn">
                            <button><span class="hdn">검색하기</span></button>
                        </div>
                    </div>
                    <div id=quick class="widget_area">
                        <div class="widget_tit mo_btn">필터를 입력하세요 <span></span></div>
                        <div class="filter_modal widget_inner">
                            <div class="mo_widget_tit">필터를 입력하세요 <span class="mo_close">&times;</span></div>

                            <ul>
                                <li><a href="#">지원영역</a>
                                    <ul>
                                        <li>
                                            <div class="col_half">
                                                <div class="radio-items">
                                                    <input id="a1" class="only-sr checked" type="checkbox" name="temp1"
                                                        value="1" checked>
                                                    <label for="a1">전체</label>
                                                </div>
                                            </div>
                                            <div class="col_half">
                                                <div class="radio-items">
                                                    <input id="a2" class="only-sr" type="checkbox" name="temp2"
                                                        value="2">
                                                    <label for="a2" style="font-size: 12px;">6차산업/도농교류</label>
                                                </div>
                                            </div>
                                            <div class="col_half">
                                                <div class="radio-items">
                                                    <input id="a3" class="only-sr" type="checkbox" name="temp3"
                                                        value="3">
                                                    <label for="a3">안전/복지</label>
                                                </div>
                                            </div>
                                            <div class="col_half">
                                                <div class="radio-items">
                                                    <input id="a4" class="only-sr" type="checkbox" name="temp4"
                                                        value="4">
                                                    <label for="a4">교육/컨설팅</label>
                                                </div>
                                            </div>
                                            <div class="col_half">
                                                <div class="radio-items">
                                                    <input id="a5" class="only-sr" type="checkbox" name="temp5"
                                                        value="5">
                                                    <label for="a5" style="font-size: 12px;">생산/유통기반확충</label>
                                                </div>
                                            </div>
                                            <div class="col_half">
                                                <div class="radio-items">
                                                    <input id="a6" class="only-sr" type="checkbox" name="temp6"
                                                        value="6">
                                                    <label for="a6">유통/마케팅</label>
                                                </div>
                                            </div>
                                            <div class="col_half">
                                                <div class="radio-items">
                                                    <input id="a7" class="only-sr" type="checkbox" name="temp7"
                                                        value="7">
                                                    <label for="a7">경영안전지원</label>
                                                </div>
                                            </div>
                                            <div class="col_half">
                                                <div class="radio-items">
                                                    <input id="a8" class="only-sr" type="checkbox" name="temp8"
                                                        value="8">
                                                    <label for="a8">귀농/귀촌/창업</label>
                                                </div>
                                            </div>

                                        </li>
                                    </ul>
                                </li>
                                <li><a href="#">성별</a>
                                    <ul>
                                        <li>
                                            <div class="col_half">
                                                <div class="radio-items">
                                                    <input id="" class="only-sr checked" type="checkbox" name="temp1"
                                                        value="1" checked>
                                                    <label for="">남자</label>
                                                </div>
                                            </div>
                                            <div class="col_half">
                                                <div class="radio-items">
                                                    <input id="" class="only-sr" type="checkbox" name="temp2" value="2">
                                                    <label for="">여자</label>
                                                </div>
                                            </div>
                                        </li>
                                    </ul>
                                </li>
                                <li><a href="#">나이</a>
                                    <ul>
                                        <li>
                                            <div class="radio-items">
                                                <label for="" class="hdn">검색어입력</label>
                                                <input id="" class="" type="text"
                                                    placeholder="나이를 입력하세요."><span>세</span>
                                            </div>
                                        </li>
                                    </ul>
                                </li>
                                <li><a href="#">거주지</a>
                                    <ul>
                                        <li>
                                            <div class="radio-items">
                                                <label for="" class="hdn">검색어입력</label>
                                                <input id="" class="" type="text" placeholder="거주지를 입력하세요.">
                                            </div>
                                        </li>
                                    </ul>
                                </li>
                                <li><a href="#">영농경력</a>
                                    <ul>
                                        <li>
                                            <div class="radio-items">
                                                <label for="" class="hdn">검색어입력</label>
                                                <input id="" class="" type="text"
                                                    placeholder="영농경력을 입력하세요."><span>년</span>
                                            </div>
                                        </li>
                                    </ul>
                                </li>
                                <li><a href="#">재배품목군</a>
                                    <ul>
                                        <li>
                                            <div class="radio-items">
                                                <label for="" class="hdn">재배품목군선택</label>
                                                <select class="" id="" name="">
                                                    <option value="" selected="selected">재배품목군을 선택하세요</option>
                                                    <option value="">과실류</option>
                                                    <option value="">과일과채류</option>
                                                    <option value="">과채류</option>
                                                </select>
                                            </div>
                                        </li>
                                    </ul>
                                </li>
                                <li><a href="#">재배품목</a>
                                    <ul>
                                        <li>
                                            <div class="radio-items">
                                                <label for="" class="hdn">재배품목선택</label>
                                                <select class="" id="" name="">
                                                    <option value="" selected="selected">재배품목을 선택하세요</option>
                                                    <option value="">조</option>
                                                    <option value="">좁쌀</option>
                                                    <option value="">수수</option>
                                                </select>
                                            </div>
                                        </li>
                                    </ul>
                                </li>
                                <li><a href="#">재배유형</a>
                                    <ul>
                                        <li>
                                            <div class="col_half">
                                                <div class="radio-items">
                                                    <input id="" class="only-sr checked" type="radio" name="temp1"
                                                        value="시설" checked>
                                                    <label for="">시설</label>
                                                </div>
                                            </div>
                                            <div class="col_half">
                                                <div class="radio-items">
                                                    <input id="" class="only-sr" type="radio" name="temp1" value="노지">
                                                    <label for="">노지</label>
                                                </div>
                                            </div>
                                        </li>
                                    </ul>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div id="lowfirm" class="tabcontent">
                    <div class="sch_form">
                        <label for="" class="hdn">검색어입력</label>
                        <input type="text" placeholder="원하는 검색 키워드를 입력하세요.">

                        <div class="sch_btn">
                            <button><span class="hdn">검색하기</span></button>
                        </div>
                    </div>
                    <div id=quick class="widget_area">
                        <div class="widget_tit mo_btn">필터를 입력하세요 <span></span></div>
                        <div class="filter_modal widget_inner">
                            <div class="mo_widget_tit">필터를 입력하세요 <span class="mo_close">&times;</span></div>

                            <ul>
                                <li><a href="#">지원영역</a>
                                    <ul>
                                        <li>
                                            <div class="col_half">
                                                <div class="radio-items">
                                                    <input id="a1" class="only-sr checked" type="checkbox" name="temp1"
                                                        value="1" checked>
                                                    <label for="a1">전체</label>
                                                </div>
                                            </div>
                                            <div class="col_half">
                                                <div class="radio-items">
                                                    <input id="a2" class="only-sr" type="checkbox" name="temp2"
                                                        value="2">
                                                    <label for="a2" style="font-size: 12px;">6차산업/도농교류</label>
                                                </div>
                                            </div>
                                            <div class="col_half">
                                                <div class="radio-items">
                                                    <input id="a3" class="only-sr" type="checkbox" name="temp3"
                                                        value="3">
                                                    <label for="a3">안전/복지</label>
                                                </div>
                                            </div>
                                            <div class="col_half">
                                                <div class="radio-items">
                                                    <input id="a4" class="only-sr" type="checkbox" name="temp4"
                                                        value="4">
                                                    <label for="a4">교육/컨설팅</label>
                                                </div>
                                            </div>
                                            <div class="col_half">
                                                <div class="radio-items">
                                                    <input id="a5" class="only-sr" type="checkbox" name="temp5"
                                                        value="5">
                                                    <label for="a5" style="font-size: 12px;">생산/유통기반확충</label>
                                                </div>
                                            </div>
                                            <div class="col_half">
                                                <div class="radio-items">
                                                    <input id="a6" class="only-sr" type="checkbox" name="temp6"
                                                        value="6">
                                                    <label for="a6">유통/마케팅</label>
                                                </div>
                                            </div>
                                            <div class="col_half">
                                                <div class="radio-items">
                                                    <input id="a7" class="only-sr" type="checkbox" name="temp7"
                                                        value="7">
                                                    <label for="a7">경영안전지원</label>
                                                </div>
                                            </div>
                                            <div class="col_half">
                                                <div class="radio-items">
                                                    <input id="a8" class="only-sr" type="checkbox" name="temp8"
                                                        value="8">
                                                    <label for="a8">귀농/귀촌/창업</label>
                                                </div>
                                            </div>

                                        </li>
                                    </ul>
                                </li>
                                <li><a href="#">지원유형</a>
                                    <ul>
                                        <li>
                                            <div class="col_half">
                                                <div class="radio-items">
                                                    <input id="" class="only-sr checked" type="checkbox" name="temp1"
                                                        value="1" checked>
                                                    <label for="">보조사업</label>
                                                </div>
                                            </div>
                                            <div class="col_half">
                                                <div class="radio-items">
                                                    <input id="" class="only-sr" type="checkbox" name="temp2" value="2">
                                                    <label for="">융자사업</label>
                                                </div>
                                            </div>
                                        </li>
                                    </ul>
                                </li>
                                <li><a href="#">지원주체</a>
                                    <ul>
                                        <li>
                                            <div class="col_half">
                                                <div class="radio-items">
                                                    <input id="" class="only-sr checked" type="checkbox" name="temp1"
                                                        value="1" checked>
                                                    <label for="">중앙정부</label>
                                                </div>
                                            </div>
                                            <div class="col_half">
                                                <div class="radio-items">
                                                    <input id="" class="only-sr" type="checkbox" name="temp2" value="2">
                                                    <label for="">지방정부</label>
                                                </div>
                                            </div>
                                        </li>
                                    </ul>
                                </li>
                                <li><a href="#">설립연도</a>
                                    <ul>
                                        <li>
                                            <div class="radio-items">
                                                <label for="" class="hdn">검색어입력</label>
                                                <input id="" class="" type="text"
                                                    placeholder="설립연도를 입력하세요."><span>년</span>
                                            </div>
                                        </li>
                                    </ul>
                                </li>
                                <li><a href="#">자본금(만원)</a>
                                    <ul>
                                        <li>
                                            <div class="radio-items">
                                                <label for="" class="hdn">검색어입력</label>
                                                <input id="" class="" type="text"
                                                    placeholder="자본금을 입력하세요."><span>만원</span>
                                            </div>
                                        </li>
                                    </ul>
                                </li>
                                <li><a href="#">출자자 수</a>
                                    <ul>
                                        <li>
                                            <div class="radio-items">
                                                <label for="" class="hdn">검색어입력</label>
                                                <input id="" class="" type="text"
                                                    placeholder="출자자수를 입력하세요."><span>명</span>
                                            </div>
                                        </li>
                                    </ul>
                                </li>
                                <li><a href="#">소재지</a>
                                    <ul>
                                        <li>
                                            <div class="radio-items">
                                                <label for="" class="hdn">검색어입력</label>
                                                <input id="" class="" type="text" placeholder="소재지를 입력하세요.">
                                            </div>
                                        </li>
                                    </ul>
                                </li>
                                <li><a href="#">주 취급품목군</a>
                                    <ul>
                                        <li>
                                            <div class="radio-items">
                                                <label for="" class="hdn">검색어선택</label>
                                                <select class="" id="" name="">
                                                    <option value="" selected="selected">주 취급품목군을 선택하세요</option>
                                                    <option value="">과실류</option>
                                                    <option value="">과일과채류</option>
                                                    <option value="">과채류</option>
                                                </select>
                                            </div>
                                        </li>
                                    </ul>
                                </li>
                                <li><a href="#">주 취급품목</a>
                                    <ul>
                                        <li>
                                            <div class="radio-items">
                                                <label for="" class="hdn">검색어선택</label>
                                                <select class="" id="" name="">
                                                    <option value="" selected="selected">주 취급품목을 선택하세요</option>
                                                    <option value="">조</option>
                                                    <option value="">좁쌀</option>
                                                    <option value="">수수</option>
                                                </select>
                                            </div>
                                        </li>
                                    </ul>
                                </li>
                                <li><a href="#">취급규모(톤)</a>
                                    <ul>
                                        <li>
                                            <div class="radio-items">
                                                <label for="" class="hdn">검색어입력</label>
                                                <input id="" class="" type="text"
                                                    placeholder="취급규모를 입력하세요."><span>톤</span>
                                            </div>
                                        </li>
                                    </ul>
                                </li>
                                <li><a href="#">매출액(만원)</a>
                                    <ul>
                                        <li>
                                            <div class="radio-items">
                                                <label for="" class="hdn">검색어입력</label>
                                                <input id="" class="" type="text"
                                                    placeholder="매출액을 입력하세요."><span>만원</span>
                                            </div>
                                        </li>
                                    </ul>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div id="be_famer" class="tabcontent">
                    <div class="sch_form">
                        <label for="" class="hdn">검색어입력</label>
                        <input type="text" placeholder="등록번호 또는 원하는 검색 키워드를 입력하세요.">

                        <div class="sch_btn">
                            <button><span class="hdn">검색하기</span></button>
                        </div>
                    </div>
                    <div id=quick class="widget_area">
                        <div class="widget_tit mo_btn">필터를 입력하세요 <span></span></div>
                        <div class="filter_modal widget_inner">
                            <div class="mo_widget_tit">필터를 입력하세요 <span class="mo_close">&times;</span></div>

                            <ul>
                                <li><a href="#">성별</a>
                                    <ul>
                                        <li>
                                            <div class="col_half">
                                                <div class="radio-items">
                                                    <input id="" class="only-sr checked" type="checkbox" name="temp1"
                                                        value="1" checked>
                                                    <label for="">남자</label>
                                                </div>
                                            </div>
                                            <div class="col_half">
                                                <div class="radio-items">
                                                    <input id="" class="only-sr" type="checkbox" name="temp2" value="2">
                                                    <label for="">여자</label>
                                                </div>
                                            </div>
                                        </li>
                                    </ul>
                                </li>
                                <li><a href="#">나이</a>
                                    <ul>
                                        <li>
                                            <div class="radio-items">
                                                <label for="" class="hdn">검색어입력</label>
                                                <input id="" class="" type="text"
                                                    placeholder="나이를 입력하세요."><span>세</span>
                                            </div>
                                        </li>
                                    </ul>
                                </li>
                                <li><a href="#">거주지</a>
                                    <ul>
                                        <li>
                                            <div class="radio-items">
                                                <label for="" class="hdn">검색어입력</label>
                                                <input id="" class="" type="text" placeholder="거주지를 입력하세요.">
                                            </div>
                                        </li>
                                    </ul>
                                </li>
                                <li><a href="#">농업계 학교 출신여부</a>
                                    <ul>
                                        <li>
                                            <div class="radio-items">
                                                <label for="" class="hdn">농업계 학교 출신여부선택</label>
                                                <select class="" id="" name="">
                                                    <option value="" selected="selected">농업계 학교 출신여부를 선택하세요</option>
                                                    <option value="">농업계 학교 출신</option>
                                                    <option value="">농업계 학교 출신 아님</option>
                                                </select>
                                            </div>
                                        </li>
                                    </ul>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>


            </div>
            <div class="tab_menu_area">
                <ul>
                    <li class="fir_ch">전체<b>37</b></li>
                    <li>경영안전지원<b>20</b></li>
                    <li>교육컨설팅<b>17</b></li>
                    <li>유사농업경영체<b>3</b></li>
                </ul>
            </div>

            <div class="result_area">
                <ul>
                    <li><a href="#"><b>귀하</b>께서 검색하신 결과를 요약하여 아래와 같이 알려드립니다.</a>
                        <ul>
                            <li>
                                <p>귀하의 조건으로 <b>검색</b>한 <b>결과</b>, <b>전체 37건</b>의 지원정책이 검색되었습니다.</p>
                                <p>분야별로 <b>경영안정지원정책 20건, 교육컨설팅지원정책 17건</b>이 검색되었으며 귀하의 조건과 유사한 <b>유사농업경영체가 지원 받은 지원정책은
                                        3건</b>이 검색되었습니다.<br />
                                    총 37건의 <b>합산금액은 약 245,000,000원</b>입니다.(금액이 산출되지 않은 지원정책은 제외되었습니다.)</p>
                            </li>
                        </ul>
                    </li>
                </ul>

            </div>
        </div>
        <div class="table_area">
            <ul>
                <li class="bdNone">
                    <div class="article_area">
                        <p class="big_tit">가축분뇨 액비살포비 지원 (가축분뇨 액비살포비 지원)</p>

                        <div class="keyword_item flNone cl">
                            <button type="button" class="item_01">중앙정부</button>
                            <button type="button" class="item_02">농업인,법인</button>
                            <button type="button" class="item_03">보조사업</button>
                            <button type="button" class="item_04">경영안전지원</button>
                        </div>
                        <p class="s_txt cl">가축분뇨처리 시설,장비 등 지원으로 가축분뇨를 퇴비,액비,에너지 등으로 자원화하여 자연순환농업 활성화 및 환경 오염 방지를 위한
                            기존정화시설 개보수를 지원</p>
                        <button type="button" class="go_btn flr">사업지침서보기</button>
                    </div>
                </li>
            </ul>

        </div>
        <div class="table_area02">
            <div class="article_area">
                <h4>지원조건</h4>
                <ul class="col_four">
                    <li>
                        <dl>
                            <dt>연령</dt>
                            <dd>65~74</dd>
                        </dl>
                    </li>
                    <li>
                        <dl>
                            <dt>농업경영체 등록 여부</dt>
                            <dd>Y</dd>
                        </dl>
                    </li>
                    <li>
                        <dl>
                            <dt>농업경영기간</dt>
                            <dd>10년 이상</dd>
                        </dl>
                    </li>
                    <li>
                        <dl>
                            <dt>대기업 제한여부</dt>
                            <dd>Y</dd>
                        </dl>
                    </li>
                    <li>
                        <dl>
                            <dt>재배 면적</dt>
                            <dd> 40,000 m² 이하 </dd>
                        </dl>
                    </li>
                    <li>
                        <dl>
                            <dt>농지소유 기간</dt>
                            <dd>3년 이상</dd>
                        </dl>
                    </li>
                    <li>
                        <dl>
                            <dt>농지이용 기간</dt>
                            <dd>3년 이상</dd>
                        </dl>
                    </li>
                    <li>
                        <dl>
                            <dt>농지기준</dt>
                            <dd>밭/논/과수원</dd>
                        </dl>
                    </li>
                </ul>
            </div>
        </div>
        <div class="table_area">
            <div class="article_area">
                <h4>기타 제한요건</h4>
                <ul class="list_type">
                    <li>거짓이나 부정한 방법으로 약정이 해제된지 5년미만인 농업인</li>
                    <li>경작 허용된 농지외의 농지를 경작하여 약정이 해지된지 3년 미만인 농업인</li>
                    <li>경영이양한 논,밭,과수원의 임대/임대위탁계약이 농업인의 귀책사유에 의해 해지된지 3년 미만인 농업인</li>
                    <li>약정이 해지 또는 해제된 경우 환수되어야 하는 금액을 완납하지 않은 농업인</li>
                </ul>
            </div>
        </div>
        <div class="table_area02">
            <div class="article_area">
                <h4>사업내용</h4>
                <ul class="list_type_half">
                    <li>농지매도시 : 330만원/ha/년, 330원/m/년</li>
                    <li>지원농지임대시 : 205만원/ha/년, 250원/m/년</li>
                </ul>
            </div>
        </div>
        <div class="table_area">
            <div class="article_area">
                <h4>지원내용</h4>
                <ul class="list_type_half">
                    <li>사용용도 : 경영안정 자금</li>
                    <li>지원금액 : 10~13백만원</li>
                    <li>지원기준 : 40,000m 한도(매도 및 임대이양 면적 합산)</li>
                    <li>재원 : 국비 100%, 지방비-%, 자부담-%, 융자-%</li>
                    <li>사업기간 : 약정기간까지(최대 80세 12월 31일까지)</li>
                    <li>신청시기 : 연중(2019년 예산 범위 내에서 연중 신청</li>
                </ul>
            </div>
        </div>
        <div class="table_area02">
            <div class="article_area">
                <h4>우대조건</h4>
                <ul class="list_type_half">
                    <li>인증 : 친환경인증</li>
                    <li>타사업참여/연계 : 논타작물재배지원</li>
                    <li>교육 : 대표 또는 관리자의 식량작물공동(들녘)경영체리더교육수료</li>
                    <li>조직형태 : 공동경영체</li>
                    <li>출하형태 : 공동출하</li>
                </ul>
            </div>
        </div>
        <div class="title_box">유사사업
            <p>밭농업직접지불보조금, 쌀소득보전고정직불(고정직불금), 경관보전직불</p>
        </div>
        <div class="table_area">
            <div class="article_area">
                <h4>통계보기</h4>
                <div class="graph_area"></div>
            </div>
        </div>

    </div>



    <div class="ico_nav">
        <ul>
            <li class="ico_menu01"><a href="#" title="">바로가기</a></li>
            <li class="ico_menu02"><a href="#" title="">바로가기</a></li>
            <li class="ico_menu03"><a href="#" title="">바로가기</a></li>
        </ul>
    </div>



    <!--tab_menu를 위한-->
    <script>
        function openCity(evt, tabname) {
            var i, tabcontent, tablinks;
            tabcontent = document.getElementsByClassName("tabcontent");
            for (i = 0; i < tabcontent.length; i++) {
                tabcontent[i].style.display = "none";
            }
            tablinks = document.getElementsByClassName("tablinks");
            for (i = 0; i < tablinks.length; i++) {
                tablinks[i].className = tablinks[i].className.replace(" active", "");
            }
            document.getElementById(tabname).style.display = "block";
            evt.currentTarget.className += " active";
        }

        // Get the element with id="defaultOpen" and click on it
        document.getElementById("defaultOpen").click();


    </script>
    <!--radio버튼을 위한-->
    <script>
        function hasClass(target, className) {
            if ((' ' + target.className + ' ').replace(/[\n\t]/g, ' ').indexOf(' ' + className + ' ') > -1) return true;
            return false;
        }
        function removeClass(target, className) {
            var elClass = ' ' + target.className + ' ';
            while (elClass.indexOf(' ' + className + ' ') !== -1) {
                elClass = elClass.replace(' ' + className + ' ', '');
            }
            target.className = elClass;
        }
        function addClass(target, className) {
            target.className += ' ' + className;
        }

        if (hasClass(document.getElementsByTagName('html')[0], 'ie8')) { // ie8 일 경우
            var radios = document.querySelectorAll('input[type="radio"]'),
                i,
                len = radios.length;

            for (i = 0; i < len; i++) {
                radios[i].attachEvent('onchange', function (e) {
                    var siblingsChecked = this.parentNode.parentNode.querySelector('.checked'); // 이전 checked 버튼

                    removeClass(siblingsChecked, 'checked'); // checked 삭제
                    addClass(this, 'checked'); // checked 부여
                });
            }
        }
    </script>
    <!--widget을 위한-->
    <script type="text/javascript">
        $('.widget_inner li a').click(function () {
            // 메뉴 a  click 이벤트가 발생 했을때 
            // 조건 : a의 형제인 ul이 존재하는지 확인 length가 0이면 false 아니면은 true
            // ul의 존재유무를 확인하는 이유는 a태그의 href연결된 사이트로 이동시킬지 아닐지 확인하기 위해서 
            if ($(this).find("+ul").length) {
                $(this).find("+ul").stop(true).slideToggle().parent().siblings().find("ul:visible").stop(true).slideUp();
                return false;
                // $(this).find("+ul").slideToggle() 
                // -> 설명 : a의 형제인 ul이 display:none이면 slideDown(dislay:block)로 실행 반대로 display:block이면 slideUp(dislay:nones)실행
                // $(this).parent().siblings().find("ul:visible").slideUp();
                // -> 설명 : a의 부모(li) 형제(li)들 중에서 자식(ul)이 visible(display:block)인 것은 slideUp 실행
            }
        });

        $('.result_area li a').click(function () {
            if ($(this).find("+ul").length) {
                $(this).find("+ul").stop(true).slideToggle().parent().siblings().find("ul:visible").stop(true).slideUp();
                return false;
            }
        });
    </script>

    <!--mobile_modal창을 위한-->
    <script>
        // Get the modal

        var modalparent = document.getElementsByClassName("filter_modal");

        // Get the button that opens the modal

        var modal_btn_multi = document.getElementsByClassName("mo_btn");

        // Get the <span> element that closes the modal
        var span_close_multi = document.getElementsByClassName("mo_close");

        // When the user clicks the button, open the modal
        function setDataIndex() {

            for (i = 0; i < modal_btn_multi.length; i++) {
                modal_btn_multi[i].setAttribute('data-index', i);
                modalparent[i].setAttribute('data-index', i);
                span_close_multi[i].setAttribute('data-index', i);
            }
        }



        for (i = 0; i < modal_btn_multi.length; i++) {
            modal_btn_multi[i].onclick = function () {
                var ElementIndex = this.getAttribute('data-index');
                modalparent[ElementIndex].style.display = "block";
            };

            // When the user clicks on <span> (x), close the modal
            span_close_multi[i].onclick = function () {
                var ElementIndex = this.getAttribute('data-index');
                modalparent[ElementIndex].style.display = "none";
            };

        }

        window.onload = function () {

            setDataIndex();
        };

        window.onclick = function (event) {
            if (event.target === modalparent[event.target.getAttribute('data-index')]) {
                modalparent[event.target.getAttribute('data-index')].style.display = "none";
            }

            // OLD CODE
            if (event.target === modal) {
                modal.style.display = "none";
            }
        };

        // Get the modal
        var modal = document.getElementById("filter_modal");

        // Get the button that opens the modal
        var btn = document.getElementById("mo_btn");

        // Get the <span> element that closes the modal
        var span = document.getElementsByClassName("mo_close")[0];

        // When the user clicks the button, open the modal 
        btn.onclick = function () {
            modal.style.display = "block";
        }

        // When the user clicks on <span> (x), close the modal
        span.onclick = function () {
            modal.style.display = "none";
        }

        // When the user clicks anywhere outside of the modal, close it
        window.onclick = function (event) {
            if (event.target == modal) {
                modal.style.display = "none";
            }
        }
    </script>


</body>

</html>