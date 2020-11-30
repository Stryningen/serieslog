let headerNode = document.querySelector("header");

let scroll_position;
let off_set_value = headerNode.clientHeight;


function offset(){ off_set_value = headerNode.clientHeight};

let check_header = _.throttle(()=>{
    scroll_position = Math.round(window.scrollY);
    if (scroll_position > off_set_value){
	headerNode.classList.add("sticky");
    } else {
	headerNode.classList.remove("sticky");
    }
    
}, 300);

window.addEventListener("resize", offset)
window.addEventListener("scroll", check_header);
