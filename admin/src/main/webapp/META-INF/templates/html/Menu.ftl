<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<link rel="stylesheet" href="${static_url}/CSS/menu.css" type="text/css" />
<link rel="stylesheet" href="${static_url}/JS/jquery-ui/jquery-ui-1.9.2.css" type="text/css" />
<script src="${static_url}/JS/jquery-1.7.2.min.js" type="text/javascript" ></script>
</head>
<body style="padding-top:10px; border-right: 1px solid #E1E2E2; background: #f5f5f5; height: 100%; margin-top: 0px; border-top: 0px;"> 

<div style="height:100%;overflow:auto;">
<#list menu as item>
  <div class="item ui-corner-all" >
      <a href="${base_url}/${item.menuSrc}" target="content_center" >${item.menuName}</a>
  </div>
</#list>
</div>

<script type="text/javascript" >
$().ready(
function(){
  $(".item a").addClass("item-a");

  $(".tab").toggle(
    function(){
      $(this).parent().find(".item").fadeOut(100);
      $(this).find(".ui-icon").removeClass("ui-icon-minusthick");
      $(this).find(".ui-icon").addClass("ui-icon-plusthick");
    },
    function(){
      $(this).parent().find(".item").fadeIn(300);
      $(this).find(".ui-icon").removeClass("ui-icon-plusthick");
      $(this).find(".ui-icon").addClass("ui-icon-minusthick");
    }
  );

  $(".item").hover(
    function(){
      $(this).addClass("item-hover");
      $(this).find("a").addClass("item-a-hover");
    },
    function(){
      $(this).removeClass("item-hover");
      $(this).find("a").removeClass("item-a-hover");
    }
  );

  $(".item").click(
    function(){
      $(".item").removeClass("item-active");
      $(".item").find("a").removeClass("item-a-active");
      $(this).addClass("item-active");
      var a_link = $(this).find("a");
      a_link.addClass("item-a-active");
      window.open(a_link.attr("href"), a_link.attr("target")); 
    }
  );
  
  $(".item").first().click();
}
);
</script>

</body>
</html>
