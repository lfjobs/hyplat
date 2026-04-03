$(function () {
    var replyPageMap = {};
    var commentList = [];
    var replyData = null;

    if (staffId !== null && staffId !== "") {
        $(".input-wrapper").show();
        $(".nologin-wrapper").hide();
    } else {
        $(".input-wrapper").hide();
        $(".nologin-wrapper").show();
    }

    $(".close").on("click", function () {
        window.parent.postMessage("close");
    });

    $("#comment-submit").on("click", function () {
        var content = $('#comment-input').val();
        console.log(content)
        console.log(replyData)
        if (replyData === null || replyData.depth === 0) {
            console.log("发表评论")
            submitComment(videoId, videoStaffId, staffId, content);
        } else if (replyData.depth === 1) {
            console.log("回复评论")
            submitReply(videoId, staffId, videoStaffId, content, replyData.commentId, "");
        } else if (replyData.depth === 2) {
            console.log("回复某人")
            submitReply(videoId, staffId, videoStaffId, content, replyData.commentId, replyData.data.staffId);
        }
        $('#comment-input').val("");
        resetInput(true);
    });

    $(".comments-container").on('click', function () {
        $("#comment-input").val("");
        resetInput(true);
        updateInput({depth: 0})
    })

    $("#comment-input").on("input", function (e) {
        var content = $(this).val()
        var disabled = content.length === 0;
        resetInput(disabled);
    });

    // $("#comment-input").on("blur", function (e) {
    // });

    function resetInput(disabled) {
        if (disabled) {
            $("#comment-submit").prop("disabled", "disabled");
            $("#comment-submit").addClass("disabled");
        } else {
            $("#comment-submit").prop("disabled", "");
            $("#comment-submit").removeClass("disabled");
        }
    }

    function updateInput(data) {
        var {videoId, videoStaffId, staffId, staffName, commentId, toStaffId, depth} = data
        if (depth === 1 || depth === 2) {
            $("#comment-input").attr("placeholder", `回复@${staffName || "匿名用户"}`);
        } else {
            $("#comment-input").attr("placeholder", `善语结善缘，恶言伤人心`);
        }
        replyData = data;
        $("#comment-input").focus();
    }

    $(document).on("click", '#more-comments', function (e) {
        checkLogin();
        loadData(videoId)
    });

    $(document).on("click", '#more-replies', function (e) {
        var commentId = $(this).attr("data");
        checkLogin();
        loadReplyData(commentId)
    });

    $(document).on("click", '.reply', function (e) {
        checkLogin();
        var replyData = JSON.parse($(this).attr('data'))
        updateInput(replyData);
    });

    function checkLogin() {
        if (staffId === null || staffId === '') {
            if (window.confirm("请先登录或注册后发表评论，是否跳转到登录页?")) {
                window.parent.jumpToLogin();
            }
        }
    }

    $(document).on("click", '.toggle-button', function (e) {
        var commentId = $(this).attr("data");
        var opened = $(this).attr("opened");
        console.log(opened)
        if (opened === "0") {
            replyPageMap[commentId] = {
                pageNumber: 0,
                pageSize: 20,
                totalPages: 1,
                totalElements: 0
            };
            loadReplyData(commentId);
            $(this).attr("opened", "1")
            $(this).text("收起")
        } else {
            replyPageMap[commentId] = undefined;
            $(`#${commentId}`).find(".toggle-button").prevAll().slideUp(200, function () {
                $(`#${commentId}`).find(".toggle-button").prevAll().remove();
            })
            $(this).attr("opened", "0")
            $(this).text("展开")
        }
    });

    // =========================================评论相关===============================================

    function loadData(videoId) {
        if (pageNumber > totalPages) {
            return;
        }
        $.ajax({
            url: basePath + "ea/dsp/sajax_ea_searchPL.jspa",
            type: "get",
            dataType: "json",
            async: true,
            data: {
                staffId: staffId,
                videoStaffId: videoStaffId,
                videoId: videoId
            },
            success: function (data) {
                console.log(data)
                var list = data.content;
                var htmls = getCommentListHtmls(list);

                $(".comments-container").find(".load-more").remove()
                $(".comments-container").append(htmls.join(""))

                commentList.push(...list);
                totalElements = data.totalElements
                totalPages = data.totalPages
                pageNumber += 1

                if (list.length < pageSize) {
                    $(`.comments-container`).children().last().after("<div class='no-more'>-·-</div>")
                } else {
                    $(`.comments-container`).children().last().after("<div id='more-comments' class='load-more'>加载更多</div>")
                }

                $("#comments-count").html(`${totalElements}`)
            },
            error: function (data) {
                console.log("关注失败");
            }
        });
    }

    function submitComment(videoId, videoStaffId, staffId, content) {
        var data = {
            videoId: videoId,
            videoStaffId: videoStaffId,
            staffId: staffId,
            content: content
        };

        $.ajax({
            url: basePath + "ea/dsp/sajax_ea_addPL.jspa",
            type: "get",
            dataType: "json",
            async: true,
            data,
            success: function (data) {
                var html = getCommentHtml(data);
                // 发表评论后，在评论容器第一行插入本次评论内容

                if (commentList.length === 0) {
                    $(".comments-container").append(html);
                } else {
                    $(".comments-container").children().first().before(html);
                }

                totalElements++;
                $("#comments-count").html(`${totalElements}`)
            },
            error: function (data) {
                console.log("评论失败");
            }
        });
    }

    function getCommentListHtmls(comments) {
        var htmls = []
        for (var i = 0; i < comments.length; i++) {
            var comment = comments[i];
            var commentHtml = getCommentHtml(comment);
            htmls.push(commentHtml);
        }
        return htmls;
    }

    function getCommentHtml(comment) {
        var staffId = comment.staffId;
        var staffName = comment.staffName || '匿名用户';
        var avatar = comment.avatar;
        var content = comment.content;
        var commentId = comment.commentId;
        var likedCount = comment.likedCount;
        var isLiked = comment.isLiked;
        var authorIsLiked = comment.authorIsLiked;
        var replyCount = comment.replyCount;

        var toStaffId = comment.toStaffId || null;
        var toStaffName = comment.toStaffName || null;

        var createdDate = dayjs(comment.createdDate, "YYYY-MM-DD HH:mm:ss");

        var friendlyTime = createdDate.fromNow();

        var data = {
            commentId: commentId,
            data: comment,
            depth: 1
        };

        return `
             <div id="${comment.commentId}" class="item-container">
                <div class="item-wrapper">
                    <img class="avatar" src="${basePath}${avatar}${toStaffId === null ? '' : toStaffName}"  onerror="this.src='${basePath}images/ea/driving/elkc/head.png'" />
                    <div class="item-content">
                        <span class="nickname">${staffName}</span>
                        <span class="content">${content}</span>
                        <span class="footer-row">
                            <span class="date-location">${friendlyTime}</span>
                            <span class="reply" data='${JSON.stringify(data)}'>回复</span>
                        </span>
                        <div class="replies-container" style="display: ${replyCount > 0 ? 'block' : 'none'}">
                            <!--当前评论的所有回复内容-->
                            <div class="toggle-button" data="${commentId}" opened="0">
                                展开
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        `;
    }

    // =========================================回复相关===============================================

    function loadReplyData(commentId) {
        var {pageNumber, pageSize, totalPages, totalElements} = replyPageMap[commentId];
        if (pageNumber > totalPages) {
            return;
        }
        $.ajax({
            url: basePath + "ea/dsp/sajax_ea_searchReplyPL.jspa",
            type: "get",
            dataType: "json",
            async: true,
            data: {
                pageNumber: pageNumber,
                pageSize: pageSize,
                videoId,
                staffId,
                videoStaffId,
                commentId
            },
            success: function (data) {
                console.log(data)
                var commentList = data.content;
                var htmls = getReplyListHtmls(commentId, commentList);
                $(`#${commentId}`).find(".toggle-button").before(htmls)

                if (commentList.length < pageSize) {
                    $(`#${commentId}`).find(".toggle-button").before("<div class='no-more'>-·-</div>")
                } else {
                    $(`#${commentId}`).find(".toggle-button").before("<div id='more-replies' data='" + commentId + "' class='load-more'>加载更多</div>")
                }

                replyPageMap[commentId].pageNumber += 1
                replyPageMap[commentId].totalElements = data.totalElements
                replyPageMap[commentId].totalPages = data.totalPages
            },
            error: function (data) {
                console.log("关注失败");
            }
        });
    }

    function submitReply(videoId, staffId, videoStaffId, content, toCommentId, toStaffId) {
        var data = {
            videoId: videoId,
            staffId: staffId,
            videoStaffId: videoStaffId,
            content: content,
            toCommentId: toCommentId,
            toStaffId: toStaffId
        };

        $.ajax({
            url: basePath + "ea/dsp/sajax_ea_addPL.jspa",
            type: "get",
            dataType: "json",
            async: true,
            data,
            success: function (data) {
                var html = getReplytHtml(toCommentId, data);
                // var toggleButton = $(".comments-container")
                //     .find("#comment-"+_comment.commentId)
                //     .find(".toggle-button");
                $(".comments-container").find(`#${toCommentId}`).find('.replies-container').show()
                $(".comments-container").find(`#${toCommentId}`).find('.replies-container').children().first().before(html);
            },
            error: function (data) {
                console.log("回复失败");
            }
        });
    }

    function getReplyListHtmls(commentId, replies) {
        var htmls = []
        for (var i = 0; i < replies.length; i++) {
            var reply = replies[i];
            var replyHtml = getReplytHtml(commentId, reply);
            htmls.push(replyHtml);
        }
        return htmls;
    }

    function getReplytHtml(commentId, reply) {
        var staffId = reply.staffId;
        var staffName = reply.staffName || '匿名用户';
        var avatar = reply.avatar;
        var content = reply.content;
        var replyId = reply.commentId;
        var likedCount = reply.likedCount;
        var isLiked = reply.isLiked;
        var authorIsLiked = reply.authorIsLiked;
        var replyCount = reply.replyCount;

        var toStaffId = reply.toStaffId || null;
        var toStaffName = reply.toStaffName || null;

        var createdDate = dayjs(reply.createdDate, "YYYY-MM-DD HH:mm:ss");
        var friendlyTime = createdDate.fromNow();

        var data = {
            commentId: commentId,
            data: reply,
            depth: 2
        };

        return `
             <div id="${replyId}" class="item-container">
                <div class="item-wrapper">
                    <img class="avatar" src="${basePath}${avatar}"  onerror="this.src='${basePath}images/ea/driving/elkc/head.png'" />
                    <div class="item-content">
                        <span class="nickname">${staffName}${toStaffId === null ? '' : (' > ' + toStaffName)}</span>
                        <span class="content">${content}</span>
                        <span class="footer-row">
                            <span class="date-location">${friendlyTime}</span>
                            <span class="reply" data='${JSON.stringify(data)}'>回复</span>
                        </span>
                    </div>
                </div>
            </div>
        `;
    }

    loadData(videoId)
})