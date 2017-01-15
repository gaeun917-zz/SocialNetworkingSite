<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/views/include/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>Home</title>
    <link rel="Stylesheet" href="/c8/styles/default.css"/>
</head>


<body>

        <form action="/team5/modifyServlet.action" method="post">
            <table>
                <tr>
                    <th>비밀번호</th>
                    <td>
                        <input type="password" id="passwd" name="passwd" style="width: 280px"/>
                    </td>
                </tr>
                <tr>
                    <th>고등학교</th>
                    <td>
                        <input type="text" id="highschool" name="highschool" style="width: 280px"/>
                    </td>
                </tr>
                <tr>
                    <th>대학교</th>
                    <td>
                        <input type="text" id="university" name="university" style="width: 280px"/>
                    </td>
                </tr>
                <tr>
                    <th>지역</th>
                    <td>
                        <input type="text" id="location" name="location" style="width: 280px"/>
                    </td>
                </tr>
                <tr>
                    <th>연락처</th>
                    <td>
                        <input type="text" id="phone" name="phone" style="width: 280px"/>
                    </td>
                </tr>
            </table>




            <%-- modifiedServlet의 Jorney:
                1. jsp의 <form>에서 action= "../modified.action" 설정
                2. method='post'이므로 data를 post office로 보냄
                2. 주소는 Servlet에 지정되어 있음: @WebServlet(value= ../modified.action)
                4. post로 받았기 때문에, doPost method 실행
                3. doPost에서
                    request는 getParameter()로 data를 Get
                    MemberInfoDAO는 setAttributeName()으로 data를 Set
                2. 받은 데이터로 SQL문을 이용해서 UPDATE
                    MemberDao를 부르고, MemberDao는 modifyuser() 수행.
                3.  modifyuser()는 DB connect -> INSERT INTO ... Value(?,?,?) SQL문 작성
                4. ?를 해결하기 위해서 connection은 statement를 받을 준비를 함 => .preparedStatement()
                5. ? 질문에 해당하는 답을 주기로함.-> 답은 jsp <form>에서 client가 <input>에 작성한 내용
                6. Servlet은 이 데이터를 memberInfo DTO에 get/set 한 후에, modifyuser(memberInfo)의 parameter로 전달한 상태임
                7. memebrInfo를 parameter로 전달받은 MemberDao! memberInfo에서 ?의 답을 가져온다
                8. 해당하는 값은 setString(n,memberInfo.getLocation());
                    첫번째 parameter: 몇 번째 ?인지 알려줌. 즉, location은 3번째 물음표?에 대한 답이므로 n=3!
                    두번째 parameter: data 출처는 어디? servlet에게서 모든 답이 담겨있는 memberInfo(DTO)를 받았다
                    그 박스를 열어보자! 거기서 getLocation()로 3번째 ?의 답을 구하고
                    이 박스(memberInfo)에 다시 set+DataType()하고, excuteUpdate();
                    그리고 열려있는 ResultSet, prepareStatement, connection은 try/catch문으로 조심조심 닫아줘야함.
                    method로 뚝딱뚝딱 세공한 답을 다시 DTO로 return 새로운 값가지고 Servlet에게 부쳐준다
                    Servlet은 마지막으로 어디로 가야될지 새로운 주소지를 client에게 전달. index.jsp로 가라고 한다


                    * (SELECT문은 excuteQuery()로 SQL 수행하는데, 이때 수행 후의 return은 ResultSet으로 받는다
                       ResultSet은 data를 테이블 형식으로 만들어 주는 고마운 녀석@.@
                       하지만, 데이터가 n*N 형식으로 되어 있으니.. pointer로 데이터를 가르켜야 함.
                       pointer는 데이터를 찾으러 다닐때 .next()로 움직이는데,
                       다른행으로 이동할때 while(rs.next())을 이용해서 거기 데이터 있니? 물어봐야함.
                       그 행에 데이터가 있으면 rs.next()는 true/1을 return해서 while문을 햄스터 wheel을 뱅글뱅글 돌리게됨
                       이 Wheel에서 열심히 달리는 햄스터들은 DTO.setAttributeName(rs.getString(columnIndex:1))라는 고단한 작업을 진행함
                         rs는 ResultSet.즉, 여기에는 모든 값이 들어 있기 때문에,
                         rs는 getDataType()하고 몇번째 행을 원해?라고 파라미터 요구!
                         첫번째 파라미터 원하면 1이라고 주면됨 원하는 값을 전해주고,
                         DTO는 이 값을 받아서 set, return은 memberInfo(DTO)
                --%>



            <div class="buttons">
                <input type="submit" value="수정하기" style="height: 25px"/>
            </div>
        </form>

</body>
</html>


