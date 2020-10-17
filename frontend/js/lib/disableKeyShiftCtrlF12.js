// function for disable key SHIFT = 16, CTRL = 17 and F12 = 123
// For to get the keycodes visit https://keycode.info/

// disable right click
$(function() {
    $(this).bind("contextmenu", function(e) {
        e.preventDefault();
    });
}); 

// Prevent F12      
$(document).keydown(function (event) {
    if (event.keyCode == 123) { // Prevent F12
        return false;
    } else if (event.ctrlKey && event.shiftKey && event.keyCode == 73) { // Prevent Ctrl+Shift+I        
        return false;
    }
});

//stop copy of content
function killCopy(e){
    return false
}
function reEnable(){
    return true
}
document.onselectstart=new Function ("return false")
    if (window.sidebar){
    document.onmousedown = killCopy;
    document.onclick = reEnable;
}

// prevent ctrl + s
$(document).bind('keydown', function(e) {
  if (e.ctrlKey && (e.which == 83)) {
    e.preventDefault();
    return false;
  } else if (e.ctrlKey && (e.which == 85)) { // prevent ctrl + u
    e.preventDefault();
    return false;
	}
});

document.addEventListener("keyup", function (e) {
    var keyCode = e.keyCode ? e.keyCode : e.which;
            if (keyCode == 44) {
                stopPrntScr();
            }
        });
function stopPrntScr() {

            var inpFld = document.createElement("input");
            inpFld.setAttribute("value", ".");
            inpFld.setAttribute("width", "0");
            inpFld.style.height = "0px";
            inpFld.style.width = "0px";
            inpFld.style.border = "0px";
            document.body.appendChild(inpFld);
            inpFld.select();
            document.execCommand("copy");
            inpFld.remove(inpFld);
        }
       function AccessClipboardData() {
            try {
                window.clipboardData.setData('text', "Access   Restricted");
            } catch (err) {
            }
        }
        setInterval("AccessClipboardData()", 300);

        $(document).keydown(function (event) {
            if (event.ctrlKey && event.shiftKey && event.keyCode == 74) { // Prevent Ctrl+Shift+J        
                return false;
            }

            if (event.ctrlKey && event.shiftKey && event.keyCode == 87) { // Prevent Ctrl+Shift+W      
                return false;
            }
        });

        $(document).keydown(function (event) {
            if (event.ctrlKey && event.shiftKey && event.keyCode == 67) { // Prevent Ctrl+Shift+C       
                return false;
            }

            if (event.ctrlKey && event.shiftKey && event.keyCode == 87) { // Prevent Ctrl+Shift+W      
                return false;
            }
        });