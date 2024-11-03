<%@ page import="model.bean.BannerImage" %><%--
  Created by IntelliJ IDEA.
  User: Tu
  Date: 12/14/2023
  Time: 9:45 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    BannerImage logo = (BannerImage) session.getAttribute("logo");
%>
<html lang="vn">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/error.css">
    <link rel="icon" href="<%=logo.getUrlImage()%>">
    <title>Lỗi mất rồi</title>
</head>
<body>
<div class="error">

    <div class="wrap">
        <div class="404">
    <pre><code>
	 <span class="green">&lt;!</span><span>DOCTYPE html</span><span class="green">&gt;</span>
<span class="orange">&lt;html&gt;</span>
    <span class="orange">&lt;style&gt;</span>
   * {
		        <span class="green">everything</span>:<span class="blue">awesome</span>;
}
     <span class="orange">&lt;/style&gt;</span>
 <span class="orange">&lt;body&gt;</span>
              ERROR 404!
				FILE NOT FOUND!
				<span class="comment">&lt;!--The file you are looking for,
					is not where you think it is.--&gt;
		</span>
 <span class="orange"></span>



        </div>
        <br />
        <span class="info">
<br />

<span class="orange">&nbsp;&lt;/body&gt;</span>

<br/>
      <span class="orange">&lt;/html&gt;</span>
            </code></pre>
    </div>
</div>


</span>
<script src="jquery/jquery-3.7.1.slim.min.js"></script>
<script src="jquery/jquery-3.7.1.min.js"></script>
<script type="text/javascript">
    $(document).keydown(function(e) {
        if(e.keyCode === 116) {
            e.preventDefault();
            window.location.href = "index.jsp";
        }
    });
</script>
</body>
</html>
