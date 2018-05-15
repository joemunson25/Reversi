<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reversi</title>
        <link rel="stylesheet" href="css/reversi.css"/>
    </head>
    <body>
        <h1>Reversi Game</h1>
        
        <table id="outerTable"><tr><th>
        <table id="gameTable">
            <tr>
                <c:forEach var="space" items="${reversi.discs}" varStatus="stat">
                    <td>
                        <c:choose>

                            <c:when test="${space == 'VALID'}">
                                <form action="placeDisc">
                                    <input type="hidden" name="position" value="${stat.index}" />
                                    <input class="valid" type="submit" value="" name="placeBtn" />
                                </form>
                            </c:when>
                            <c:when test="${space == 'LIGHT'}">
                                <div class="light disc"></div>
                            </c:when>
                            <c:when test="${space == 'DARK'}">
                                <div class="dark disc"></div>
                            </c:when>
                              
                                

                        </c:choose>

                        <c:if test="${stat.count % 8 == 0 && stat.count < 64}">
                    </tr><tr>
                    </c:if>

                    </td>

                </c:forEach>
            </tr>
        </table></th><th>

        <table id="scoreTable">
            <tr><th colspan="2"><h2>Score Board</h2></th></tr>
            <tr>
                <td class="score">${reversi.scoreOne}</td>
                <td class="score">${reversi.scoreTwo}</td>

            </tr>
            <tr>
                
                <td class="${reversi.currentPlayer == 1 ? 'current' : ''}">Player 1 (Dark)</td>
                <td class="${reversi.currentPlayer == 2 ? 'current' : ''}">Player 2 (Light)</td>
            </tr>

            </tr>
        </table>
        </th></tr></table>

        <c:if test="${reversi.pass}">
            No valid moves!
            <form action="placeDisc">
                <input type="hidden" name="position" value="-1" />
                <input type="submit" value="Pass" name="placeBtn" />
            </form>
        </c:if>

        <c:if test="${reversi.gameOver}">
            <h2>Game over. ${reversi.winner}</h2>
        </c:if>

        <br>
        <form action="reset">
            <input type="submit" value="Reset Game" name="resetBtn" />
        </form>

    </body>
</html>
