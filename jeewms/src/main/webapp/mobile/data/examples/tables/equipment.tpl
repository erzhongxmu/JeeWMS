<header class="slidePanel-header">
    <div class="slidePanel-actions" aria-label="操作" role="group">
        <button type="button" class="btn btn-pure btn-inverse slidePanel-close actions-top icon wb-close" aria-hidden="true"></button>
        <div class="btn-group actions-bottom" role="group">
            <button type="button" class="btn btn-pure btn-inverse icon wb-chevron-left" aria-hidden="true"></button>
            <button type="button" class="btn btn-pure btn-inverse icon wb-chevron-right" aria-hidden="true"></button>
        </div>
    </div>
    <h1>关于向List集合中添加重复数据，合并的问题</h1>
</header>
<div class="slidePanel-inner">
    <section class="slidePanel-inner-section">
        <div class="forum-header">
            <a class="avatar" href="javascript:;">
                <img src="/images/portraits/2.jpg" alt="...">
            </a>
            <span class="name">竹林心风</span>
            <span class="time">3 分钟前</span>
        </div>
        <div class="forum-content">
            <p>有这样的一个需求：</p>
            <p>例：list集合中存放了几个菜，其中一道是土豆丝，（包括：名称，数量，单价），现在我在往数组内存一道菜，也是土豆丝，我怎样将这两个相同的土豆丝合并在一起，并且将数量变为2。到目前，没思路，谢谢了，看到的指点一下，谢谢了。</p>
        </div>
        <div class="forum-metas">
            <div class="button-group tags">
                标签：
                <button type="button" class="btn btn-outline btn-default">Java</button>
                <button type="button" class="btn btn-outline btn-default">求助</button>
            </div>
            <div class="pull-right">
                <button type="button" class="btn btn-icon btn-pure btn-default">
          <i class="icon wb-thumb-up" aria-hidden="true"></i>
          <span class="num">2</span>
        </button>
            </div>
            <div class="button-group share">
                分享到：
                <button type="button" class="btn btn-icon btn-pure btn-default"><i class="icon bd-weibo" aria-hidden="true"></i></button>
                <button type="button" class="btn btn-icon btn-pure btn-default"><i class="icon bd-zhihu" aria-hidden="true"></i></button>
                <button type="button" class="btn btn-icon btn-pure btn-default"><i class="icon bd-douban" aria-hidden="true"></i></button>
            </div>
        </div>
    </section>
    <section class="slidePanel-inner-section">
        <div class="forum-header">
            <div class="pull-right">#
                <span class="floor">1</span>
            </div>
            <a class="avatar" href="javascript:;">
                <img src="/images/portraits/2.jpg" alt="...">
            </a>
            <span class="name">唱不完的离歌</span>
            <span class="time">2 分钟前</span>
        </div>
        <div class="forum-content">
            <p>如果你的一道菜是一个bean的话，把bean的equals()方法重写下，改为根据名称判断。然后在新加入元素时进行判断，equals就在把数量+1.</p>
            <div class="pull-right">
                <button type="button" class="btn btn-icon btn-pure btn-default">
          <i class="icon wb-thumb-up" aria-hidden="true"></i>
          <span class="num">2</span>
        </button>
            </div>
        </div>
    </section>
    <div class="slidePanel-comment">
        <textarea class="maxlength-textarea form-control mb-sm margin-bottom-20" rows="4"></textarea>
        <button class="btn btn-primary" data-dismiss="modal" type="button">回复</button>
    </div>
</div>
