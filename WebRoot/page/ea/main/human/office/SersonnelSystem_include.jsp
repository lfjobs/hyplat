<script type="text/javascript">	
		var curOrgID = '${curOrganizationID}';
		var url = basePath + 'page/ea/main/navigation/commoncontent.jsp?'
		if (typeof(curOrgID)=='undefined'){
			
		}else
		{
			if (curOrgID!=null && curOrgID!='')
			{
				url += "curOrganizationID=" + curOrgID;
			}
		}			
		window.parent.document.getElementById('rightFrame').src = url; 
</script>