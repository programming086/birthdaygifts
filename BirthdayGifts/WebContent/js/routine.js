tinyMCE.init({
	mode : "textareas",
	theme : "advanced",
	language : "ru",
	save_callback : "customSave",
	plugins : "table,contextmenu,paste",
	theme_advanced_buttons3_add : "fontselect,fontsizeselect",
	theme_advanced_buttons2_add : "forecolor,backcolor",
	theme_advanced_buttons2_add_before: "cut,copy,paste,pastetext,pasteword,separator,search,replace",
	theme_advanced_toolbar_location : "top",
	theme_advanced_toolbar_align : "left",
	theme_advanced_path_location : "bottom",
	plugin_insertdate_dateFormat : "%Y-%m-%d",
	plugin_insertdate_timeFormat : "%H:%M:%S",
	extended_valid_elements : "hr[class|width|size|noshade],font[face|size|color|style],span[class|align|style],p[lang]",
	theme_advanced_resize_horizontal : true,
	theme_advanced_resizing : true,
	apply_source_formatting : true
});

function customSave(id, content) {
	document.getElementById(id).value = content;
//	if (id == "idNewsBody:idInBriefText") document.getElementById("idNewsBody:idInBriefText").value = content;
//	if (id == "idNewsBody:idInText") document.getElementById("idNewsBody:idInText").value = content;
}


function onEndCrop( coords, dimensions ) {
	$( 'idImageCropProps:x1' ).value = coords.x1;
	$( 'idImageCropProps:y1' ).value = coords.y1;
	$( 'idImageCropProps:width' ).value = dimensions.width;
	$( 'idImageCropProps:height' ).value = dimensions.height;
}

function crop(){
//	alert("aaa1");
	new Cropper.Img( 
			'idCropbox',
			{
				minWidth: 90, 
				minHeight: 60,
				displayOnInit: true, 
				ratioDim: { x: 90, y: 60 },
				onEndCrop: onEndCrop,
				previewWrap: 'previewArea'
			}
		); 
}
function copyClipboard(str)
{
	alert("aaa");
	if( window.clipboardData){
    	alert("Скопировано в буфер: " + str);
    	window.clipboardData.setData( 'text', str);
    	return false;
	}
	else
	{
//		   // This is importent but it's not noted anywhere
//		   netscape.security.PrivilegeManager.enablePrivilege('UniversalXPConnect');
//		   
//		   // create interface to the clipboard
//		   var clip = Components.classes['@mozilla.org/widget/clipboard;[[[[1]]]]'].createInstance(Components.interfaces.nsIClipboard);
//		   if (!clip) return;
//		   
//		   // create a transferable
//		   var trans = Components.classes['@mozilla.org/widget/transferable;[[[[1]]]]'].createInstance(Components.interfaces.nsITransferable);
//		   if (!trans) return;
//		   
//		   // specify the data we wish to handle. Plaintext in this case.
//		   trans.addDataFlavor('text/unicode');
//		   
//		   // To get the data from the transferable we need two new objects
//		   var str = new Object();
//		   var len = new Object();
//		   
//		   var str = Components.classes["@mozilla.org/supports-string;[[[[1]]]]"].createInstance(Components.interfaces.nsISupportsString);
//		   
//		   var copytext=text;
//		   
//		   str.data=copytext;
//		   
//		   trans.setTransferData("text/unicode",str,copytext.length*[[[[2]]]]);
//		   
//		   var clipid=Components.interfaces.nsIClipboard;
//		   
//		   if (!clip) return false;
//		   
//		   clip.setData(trans,null,clipid.kGlobalClipboard);
//		   
//		   alert("Following info was copied to your clipboard:\n\n" + text);
//		   
//		   return false;
		var flashcopier = 'flashcopier';
	    if(!document.getElementById(flashcopier)) {
	      var divholder = document.createElement('div');
	      divholder.id = flashcopier;
	      document.body.appendChild(divholder);
	    }
	    document.getElementById(flashcopier).innerHTML = '';
	    var divinfo = '<embed src="clipboard.swf" FlashVars="clipboard='+encodeURIComponent(str)+'" width="0" height="0" type="application/x-shockwave-flash"></embed>';
	    document.getElementById(flashcopier).innerHTML = divinfo;
	    alert("Скопировано в буфер: " + str);
    }
}
