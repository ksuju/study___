<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">

    <!-- viewport meta -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="MartPlace - Complete Online Multipurpose Marketplace HTML Template">
    <meta name="keywords" content="marketplace, easy digital download, digital product, digital, html5">

    <title>포트폴리오</title>

    <!-- inject:css -->
    <link rel="stylesheet" href="<%=ctx%>/assest/template/css/animate.css">
    <link rel="stylesheet" href="<%=ctx%>/assest/template/css/font-awesome.min.css">
    <link rel="stylesheet" href="<%=ctx%>/assest/template/css/fontello.css">
    <link rel="stylesheet" href="<%=ctx%>/assest/template/css/jquery-ui.css">
    <link rel="stylesheet" href="<%=ctx%>/assest/template/css/lnr-icon.css">
    <link rel="stylesheet" href="<%=ctx%>/assest/template/css/owl.carousel.css">
    <link rel="stylesheet" href="<%=ctx%>/assest/template/css/slick.css">
    <link rel="stylesheet" href="<%=ctx%>/assest/template/css/trumbowyg.min.css">
    <link rel="stylesheet" href="<%=ctx%>/assest/template/css/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="<%=ctx%>/assest/template/css/style.css">
	<link rel="stylesheet" href="<%=ctx%>/assest/template/css/trumbowyg.min.css">
    <!-- endinject -->

    <!-- Favicon -->
    <link rel="icon" type="image/png" sizes="16x16" href="<%=ctx%>/assest/template/images/favicon.png">    
	<script type="text/javascript">
		var ctx = '<%= request.getContextPath() %>';
	</script>	
	<script src="<%=ctx%>/assest/js/page.js"></script>
	
    <script src="<%=ctx%>/assest/template/js/vendor/trumbowyg.min.js"></script>
    <script src="<%=ctx%>/assest/template/js/vendor/trumbowyg/ko.js"></script>
    <script type="text/javascript">
	    $('#trumbowyg-demo').trumbowyg({
	        lang: 'kr'
	    });
	    
	    
	    function thumbUp(boardSeq,boardTypeSeq) {
	    	console.log("thumbUp");
	    	console.log(boardSeq);
	    	console.log(boardTypeSeq);
	    	
	    	let url = '<%=ctx%>/forum/notice/thumb-up.do?';
	    	url += 'boardSeq='+boardSeq;
	    	url += '&boardTypeSeq='+boardTypeSeq;
	    	url += '&voteType=up';
	    	
	    	
	    	
	    	$.ajax({    type : 'GET',           
	    		// 타입 (get, post, put 등등)    
	    		url : url,
	    		// 요청할 서버url
	    		headers : {
	    			// Http header
	    			"Content-Type" : "application/json"
	    		},
	    		dataType : 'text',
	    		success : function(result) {
	    			// 결과 성공 콜백함수
	    			console.log("thumbUp=====result=======>");
	    			console.log(result);
	    			
	    			if(result==0){
						$('a#cThumbUpAnchor').removeClass('active');				
	    			} else {
	    				$('a#cThumbUpAnchor').addClass('active');
	    			}
	    		},
	    		error : function(request, status, error) {
	    			// 결과 에러 콜백함수
	    			console.log(error)
	    		}
	    	});
	    }
	    
	    
	    function thumbDown(boardSeq,boardTypeSeq) {
	    	
	    	<%-- let url = '<%=ctx%>/forum/notice/thumb-down.do?';
	    	url += 'boardSeq='+boardSeq;
	    	url += '&boardTypeSeq='+boardTypeSeq; --%>
	    	
	    	let url = '<%=ctx%>/forum/notice';
	    	url += '/'+boardTypeSeq;
	    	url += '/'+boardSeq;
	    	url += '/thumb-down.do?voteType=down';
	    	
	    	
	    	//좋아요 눌러진 상태라면
	    	if($('a#cThumbUpAnchor.active').length == 1) {
	    		url += '&isLike=F';
	    		url += '&oldIsLike=T';
	    	}
	    	
	    	console.log(url);
	    	
	    	$.ajax({    type : 'GET',           
	    		// 타입 (get, post, put 등등)    
	    		url : url,
	    		// 요청할 서버url
	    		headers : {
	    			// Http header
	    			"Content-Type" : "application/json"
	    		},
	    		dataType : 'text',
	    		success : function(result) {
	    			// 결과 성공 콜백함수 
	    			console.log("thumbDown=====result=======>");
	    			console.log(result);
	    			
	    			if(result==0){
						$('a#cThumbDownAnchor').removeClass('active');				
	    			} else {
	    				$('a#cThumbDownAnchor').addClass('active');
	    			}
	    		},
	    		error : function(request, status, error) {
	    			// 결과 에러 콜백함수
	    			console.log("thumbDown=====error=======>");
	    			console.log(error)
	    		}
	    	});
	    }
	</script>
</head>

<body class="preload home1 mutlti-vendor">
    <!--================================
            START DASHBOARD AREA
    =================================-->
    <section class="support_threads_area section--padding2">
        <div class="container">
            <div class="row">
                <div class="col-lg-12">
                    <div class="forum_detail_area ">
                        <div class="cardify forum--issue">
                            <div class="title_vote clearfix">
                                <!-- <h3>Responsive Website Footer Menu</h3> -->
                                <h3><c:out value="${board.title}"/></h3>

                                <div class="vote">
                                    <a href="#" id="cThumbUpAnchor" onClick="javascript:thumbUp(${board.boardSeq},${board.boardTypeSeq});" <c:if test='${ynLike==1}'>class = "active"</c:if>>
                                        <span class="lnr lnr-thumbs-up"></span>
                                    </a>
                                    <a href="#" id="cThumbDownAnchor" onClick="javascript:thumbDown(${board.boardSeq},${board.boardTypeSeq});" <c:if test='${ynDisLike==1}'>class = "active"</c:if>>
                                        <span class="lnr lnr-thumbs-down"></span>
                                    </a>
                                </div>
                                <!-- end .vote -->
                            </div>
                            <!-- end .title_vote -->
                            <div class="suppot_query_tag">
                                <img class="poster_avatar" src="<%=ctx%>/assest/template/images/support_avat1.png" alt="Support Avatar"> <c:out value="${board.memberId}"/>
                                <span><c:out value="${board.regDtm}"/></span>
                            </div>
                            <p style="    margin-bottom: 0; margin-top: 19px;">
                            	<c:out value="${board.content}"/>
                            </p>
                        </div>
                        <form action="updatePage.do">
	                        <input type="hidden" name="boardSeq" value="${board.boardSeq}"/>
	                        <input type="hidden" name="title" value="${board.title}"/>
	                        <input type="hidden" name="content" value="${board.content}"/>
	                        <input type="hidden" name="regDtm" value="${board.regDtm}"/>
	                        <input type="hidden" name="memberId" value="${board.memberId}"/>
							<button type="submit" class="btn btn--round btn--bordered btn-sm btn-secondary">수정</button>
                        </form>
                        <!-- end .forum_issue -->


                        <div class="forum--replays cardify">
                            <div class="area_title">
                                <h4>1 Replies</h4>
                            </div>
                            <!-- end .area_title -->

                            <div class="forum_single_reply">
                                <div class="reply_content">
                                    <div class="name_vote">
                                        <div class="pull-left">
                                            <h4>AazzTech
                                                <span>staff</span>
                                            </h4>
                                            <p>Answered 3 days ago</p>
                                        </div>
                                        <!-- end .pull-left -->

                                        <div class="vote">
                                            <a href="#" class="active">
                                                <span class="lnr lnr-thumbs-up"></span>
                                            </a>
                                            <a href="#" class="">
                                                <span class="lnr lnr-thumbs-down"></span>
                                            </a>
                                        </div>
                                    </div>
                                    <!-- end .vote -->
                                    <p>Nunc placerat mi id nisi interdum mollis. Praesent pharetra, justo ut sceleris que the
                                        mattis, leo quam aliquet congue placerat mi id nisi interdum mollis. </p>
                                </div>
                                <!-- end .reply_content -->
                            </div>
                            <!-- end .forum_single_reply -->

                            <div class="comment-form-area">
                                <h4>Leave a comment</h4>
                                <!-- comment reply -->
                                <div class="media comment-form support__comment">
                                    <div class="media-left">
                                        <a href="#">
                                            <img class="media-object" src="<%=ctx%>/assest/template/images/m7.png" alt="Commentator Avatar">
                                        </a>
                                    </div>
                                    <div class="media-body">
                                        <form action="#" class="comment-reply-form">
                                            <div id="trumbowyg-demo"></div>
                                            <button class="btn btn--sm btn--round">Post Comment</button>
                                        </form>
                                    </div>
                                </div>
                                <!-- comment reply -->
                            </div>
                        </div>
                        <!-- end .forum_replays -->
                    </div>
                    <!-- end .forum_detail_area -->
                </div>
                <!-- end .col-md-12 -->            
            </div>
            <!-- end .row -->
        </div>
        <!-- end .container -->
    </section>
    <!--================================
            END DASHBOARD AREA
    =================================-->
   	<!--//////////////////// JS GOES HERE ////////////////-->
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA0C5etf1GVmL_ldVAichWwFFVcDfa1y_c"></script>
    <!-- inject:js -->
    <script src="<%=ctx%>/assest/template/js/vendor/jquery/jquery-1.12.3.js"></script>
    <script src="<%=ctx%>/assest/template/js/vendor/jquery/popper.min.js"></script>
    <script src="<%=ctx%>/assest/template/js/vendor/jquery/uikit.min.js"></script>
    <script src="<%=ctx%>/assest/template/js/vendor/bootstrap.min.js"></script>
    <script src="<%=ctx%>/assest/template/js/vendor/chart.bundle.min.js"></script>
    <script src="<%=ctx%>/assest/template/js/vendor/grid.min.js"></script>
    <script src="<%=ctx%>/assest/template/js/vendor/jquery-ui.min.js"></script>
    <script src="<%=ctx%>/assest/template/js/vendor/jquery.barrating.min.js"></script>
    <script src="<%=ctx%>/assest/template/js/vendor/jquery.countdown.min.js"></script>
    <script src="<%=ctx%>/assest/template/js/vendor/jquery.counterup.min.js"></script>
    <script src="<%=ctx%>/assest/template/js/vendor/jquery.easing1.3.js"></script>
    <script src="<%=ctx%>/assest/template/js/vendor/owl.carousel.min.js"></script>
    <script src="<%=ctx%>/assest/template/js/vendor/slick.min.js"></script>
    <script src="<%=ctx%>/assest/template/js/vendor/tether.min.js"></script>
    <script src="<%=ctx%>/assest/template/js/vendor/trumbowyg.min.js"></script>
    <script src="<%=ctx%>/assest/template/js/vendor/waypoints.min.js"></script>
    <script src="<%=ctx%>/assest/template/js/dashboard.js"></script>
    <script src="<%=ctx%>/assest/template/js/main.js"></script>
    <script src="<%=ctx%>/assest/template/js/map.js"></script>
    <!-- endinject -->
</body>

</html>
	