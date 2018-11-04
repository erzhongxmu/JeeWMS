<header class="slidePanel-header">
    <div class="overlay-top overlay-panel overlay-background bg-light-green-600">
        <div class="slidePanel-actions btn-group" aria-label="操作" role="group">
            <div class="dropdown pull-left">
                <button type="button" class="btn btn-pure dropdown-toggle icon wb-calendar" data-toggle="dropdown" aria-hidden="true"></button>
                <div class="dropdown-menu dropdown-menu-right bullet taskboard-task-datepicker">
                    <div id="taskDatepicker"></div>
                    <input type="hidden" id="taskDatepickerInput" />
                    <div class="padding-10">
                        <button class="btn btn-primary due-date-save">保存</button>
                        <a class="btn btn-sm btn-white due-date-delete" href="javascript:;">删除</a>
                    </div>
                </div>
            </div>
            <button type="button" class="btn btn-pure icon wb-list-bulleted subtask-toggle" aria-hidden="true"></button>
            <div class="fileupload pull-left">
                <button id="fileuploadToggle" type="button" class="btn btn-pure icon wb-paperclip" aria-hidden="true"></button>
                <input id="fileupload" type="file" name="upload">
            </div>
            <div class="dropdown pull-left">
                <button type="button" class="btn btn-pure dropdown-toggle icon wb-chevron-down" data-toggle="dropdown" aria-hidden="true"></button>
                <ul class="dropdown-menu dropdown-menu-right bullet" role="menu">
                    <li role="presentation" class="taskboard-task-edit">
                        <a href="javascript:;" role="menuitem"><i class="icon wb-pencil" aria-hidden="true"></i>编辑</a>
                    </li>
                    <li role="presentation" class="taskboard-task-delete">
                        <a href="javascript:;" role="menuitem"><i class="icon wb-trash" aria-hidden="true"></i>删除</a>
                    </li>
                </ul>
            </div>
            <button type="button" class="btn btn-pure slidePanel-close icon wb-close" aria-hidden="true"></button>
        </div>
        <h4 class="stage-name"></h4>
    </div>
</header>
<div class="slidePanel-inner">
    <section class="slidePanel-inner-section">
        <div class="task-main">
            <div class="task-main-surface">
                <div class="priority pull-right">
                    <label class="margin-right-20">优先级：</label>
                    <ul class="list-unstyled list-inline inline-block">
                        <li class="radio-custom radio-normal">
                            <input type="radio" class="icheckbox-grey" id="priorityNormal" data-priority="normal" name="priorities">
                            <label for="priorityNormal">普通</label>
                        </li>
                        <li class="radio-custom radio-high">
                            <input type="radio" class="icheckbox-grey" id="priorityHigh" data-priority="high" name="priorities">
                            <label for="priorityHigh">高</label>
                        </li>
                        <li class="radio-custom radio-urgent">
                            <input type="radio" class="icheckbox-grey" id="priorityUrgent" data-priority="urgent" name="priorities">
                            <label for="priorityUrgent">紧急</label>
                        </li>
                    </ul>
                </div>
                <div class="caption task-title"></div>
                <div class="add-members">
                    <select multiple="multiple" data-plugin="jquery-selective"></select>
                </div>

                <div class="description">
                    <p class="description-content"></p>
                    <a href="#" class="description-toggle">添加描述</a>
                </div>
            </div>
            <div class="task-main-editor">
                <form>
                    <div class="form-group">
                        <input id="task-title" class="form-control" type="text" name="title">
                    </div>
                    <div class="form-group">
                        <textarea id="task-description" name="content" data-provide="markdown" data-iconlibrary="fa" rows="6"></textarea>
                    </div>
                    <div class="form-group">
                        <button class="btn btn-primary task-main-editor-save" type="button">保存</button>
                        <a class="btn btn-sm btn-white task-main-editor-cancel" href="javascript:;">取消</a>
                    </div>
                </form>
            </div>
        </div>
        <div class="task-section subtasks">
            <label><i class="icon wb-list-bulleted margin-right-10"></i>子任务</label>
            <ul class="list-group list-group-full subtasks-list"></ul>
            <div class="subtasks-add">
                <form>
                    <div class="form-group">
                        <input class="form-control subtask-title" type="text" name="title">
                    </div>
                    <div class="form-group">
                        <button class="btn btn-primary subtask-add-save" type="button">保存</button>
                        <a class="btn btn-sm btn-white subtask-add-cancel" href="javascript:;">取消</a>
                    </div>
                </form>
            </div>
        </div>
        <div class="task-section attachments">
            <label><i class="icon wb-paperclip margin-right-10"></i>附件 | <a href="#">下载所有</a></label>
            <ul class="list-group attachments-list"></ul>
        </div>
    </section>
    <section class="slidePanel-inner-section">
        <div class="comments">
            <label><i class="icon wb-chat-group margin-right-10"></i>评论</label>
            <div class="comments-history"></div>
            <div class="media">
                <div class="media-left">
                    <a class="avatar avatar-lg" href="javascript:;">
                        <img src="/images/portraits/5.jpg" alt="...">
                    </a>
                </div>
                <div class="media-body">
                    <div class="comment-body">
                        <form class="comment-reply margin-top-5" method="post" action="#">
                            <textarea class="form-control margin-bottom-15" placeholder="回复" rows="4"></textarea>
                            <button class="btn btn-primary" type="button" data-dismiss="modal">回复</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>
