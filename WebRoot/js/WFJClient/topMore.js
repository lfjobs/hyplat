 




document.getElementById("indexTop").onload=function(){
  	    	var doc=document.getElementById("indexTop").contentWindow.document;
  	    	doc.getElementById("topbar_menu").onclick=function(){
  	  	  	 	if(!this.isok){
  	  	  	 		this.isok=true;
  	  	 	    	document.getElementById("menuMore").style.display="block";
  	  	  	 	}else{
  	  	  	 		this.isok=false;
  	  	  	 		document.getElementById("menuMore").style.display="none";
  	  	  	 	}
  	  	 	}
  	    	doc.getElementById("return").onclick=function(){
  				window.history.go(-1);
  				}	
  	    	doc.getElementById("topbar_refresh").onclick=function(){
  				window.history.go(0);
  				}	
  	    }