// Avoid `console` errors in browsers that lack a console.
(function() {
    var method;
    var noop = function () {};
    var methods = [
        'assert', 'clear', 'count', 'debug', 'dir', 'dirxml', 'error',
        'exception', 'group', 'groupCollapsed', 'groupEnd', 'info', 'log',
        'markTimeline', 'profile', 'profileEnd', 'table', 'time', 'timeEnd',
        'timeStamp', 'trace', 'warn'
    ];
    var length = methods.length;
    var console = (window.console = window.console || {});

    while (length--) {
        method = methods[length];

        // Only stub undefined methods.
        if (!console[method]) {
            console[method] = noop;
        }
    }
}());

/*! Overthrow v.0.1.0. An overflow:auto polyfill for responsive design. (c) 2012: Scott Jehl, Filament Group, Inc. http://filamentgroup.github.com/Overthrow/license.txt */
(function(w,undefined){var doc=w.document,docElem=doc.documentElement,classtext="overthrow-enabled",canBeFilledWithPoly="ontouchmove"in doc,overflowProbablyAlreadyWorks="WebkitOverflowScrolling"in docElem.style||!canBeFilledWithPoly&&w.screen.width>1200||function(){var ua=w.navigator.userAgent,webkit=ua.match(/AppleWebKit\/([0-9]+)/),wkversion=webkit&&webkit[1],wkLte534=webkit&&wkversion>=534;return ua.match(/Android ([0-9]+)/)&&RegExp.$1>=3&&wkLte534||ua.match(/ Version\/([0-9]+)/)&&RegExp.$1>=0&&
w.blackberry&&wkLte534||ua.indexOf(/PlayBook/)>-1&&RegExp.$1>=0&&wkLte534||ua.match(/Fennec\/([0-9]+)/)&&RegExp.$1>=4||ua.match(/wOSBrowser\/([0-9]+)/)&&RegExp.$1>=233&&wkLte534||ua.match(/NokiaBrowser\/([0-9\.]+)/)&&parseFloat(RegExp.$1)===7.3&&webkit&&wkversion>=533}(),defaultEasing=function(t,b,c,d){return c*((t=t/d-1)*t*t+1)+b},enabled=false,timeKeeper,toss=function(elem,options){var i=0,sLeft=elem.scrollLeft,sTop=elem.scrollTop,o={top:"+0",left:"+0",duration:100,easing:w.overthrow.easing},endLeft,
endTop;if(options)for(var j in o)if(options[j]!==undefined)o[j]=options[j];if(typeof o.left==="string"){o.left=parseFloat(o.left);endLeft=o.left+sLeft}else{endLeft=o.left;o.left=o.left-sLeft}if(typeof o.top==="string"){o.top=parseFloat(o.top);endTop=o.top+sTop}else{endTop=o.top;o.top=o.top-sTop}timeKeeper=setInterval(function(){if(i++<o.duration){elem.scrollLeft=o.easing(i,sLeft,o.left,o.duration);elem.scrollTop=o.easing(i,sTop,o.top,o.duration)}else{if(endLeft!==elem.scrollLeft)elem.scrollLeft=endLeft;
if(endTop!==elem.scrollTop)elem.scrollTop=endTop;intercept()}},1);return{top:endTop,left:endLeft,duration:o.duration,easing:o.easing}},closest=function(target,ascend){return!ascend&&target.className&&target.className.indexOf("overthrow")>-1&&target||closest(target.parentNode)},intercept=function(){clearInterval(timeKeeper)},enable=function(){if(enabled)return;enabled=true;if(overflowProbablyAlreadyWorks||canBeFilledWithPoly)docElem.className+=" "+classtext;w.overthrow.forget=function(){docElem.className=
docElem.className.replace(classtext,"");if(doc.removeEventListener)doc.removeEventListener("touchstart",start,false);w.overthrow.easing=defaultEasing;enabled=false};if(overflowProbablyAlreadyWorks||!canBeFilledWithPoly)return;var elem,lastTops=[],lastLefts=[],lastDown,lastRight,resetVertTracking=function(){lastTops=[];lastDown=null},resetHorTracking=function(){lastLefts=[];lastRight=null},finishScroll=function(){var top=(lastTops[0]-lastTops[lastTops.length-1])*8,left=(lastLefts[0]-lastLefts[lastLefts.length-
1])*8,duration=Math.max(Math.abs(left),Math.abs(top))/8;top=(top>0?"+":"")+top;left=(left>0?"+":"")+left;if(!isNaN(duration)&&duration>0&&(Math.abs(left)>80||Math.abs(top)>80))toss(elem,{left:left,top:top,duration:duration})},inputs,setPointers=function(val){inputs=elem.querySelectorAll("textarea, input");for(var i=0,il=inputs.length;i<il;i++)inputs[i].style.pointerEvents=val},changeScrollTarget=function(startEvent,ascend){if(doc.createEvent){var newTarget=(!ascend||ascend===undefined)&&elem.parentNode||
elem.touchchild||elem,tEnd;if(newTarget!==elem){tEnd=doc.createEvent("HTMLEvents");tEnd.initEvent("touchend",true,true);elem.dispatchEvent(tEnd);newTarget.touchchild=elem;elem=newTarget;newTarget.dispatchEvent(startEvent)}}},start=function(e){intercept();resetVertTracking();resetHorTracking();elem=closest(e.target);if(!elem||elem===docElem||e.touches.length>1)return;setPointers("none");var touchStartE=e,scrollT=elem.scrollTop,scrollL=elem.scrollLeft,height=elem.offsetHeight,width=elem.offsetWidth,
startY=e.touches[0].pageY,startX=e.touches[0].pageX,scrollHeight=elem.scrollHeight,scrollWidth=elem.scrollWidth,move=function(e){var ty=scrollT+startY-e.touches[0].pageY,tx=scrollL+startX-e.touches[0].pageX,down=ty>=(lastTops.length?lastTops[0]:0),right=tx>=(lastLefts.length?lastLefts[0]:0);if(ty>0&&ty<scrollHeight-height||tx>0&&tx<scrollWidth-width)e.preventDefault();else changeScrollTarget(touchStartE);if(lastDown&&down!==lastDown)resetVertTracking();if(lastRight&&right!==lastRight)resetHorTracking();
lastDown=down;lastRight=right;elem.scrollTop=ty;elem.scrollLeft=tx;lastTops.unshift(ty);lastLefts.unshift(tx);if(lastTops.length>3)lastTops.pop();if(lastLefts.length>3)lastLefts.pop()},end=function(e){finishScroll();setPointers("auto");setTimeout(function(){setPointers("none")},450);elem.removeEventListener("touchmove",move,false);elem.removeEventListener("touchend",end,false)};elem.addEventListener("touchmove",move,false);elem.addEventListener("touchend",end,false)};doc.addEventListener("touchstart",
start,false)};w.overthrow={set:enable,forget:function(){},easing:defaultEasing,toss:toss,intercept:intercept,closest:closest,support:overflowProbablyAlreadyWorks?"native":canBeFilledWithPoly&&"polyfilled"||"none"};enable()})(this);