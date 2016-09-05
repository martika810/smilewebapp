require.config({
	waitSeconds:200,
	paths:{
		'jquery': 'lib/jquery-1.11.3.min',
		'cloudinary' : 'lib/jquery.cloudinary',
		'static': 'static',
		'coyote' : 'coyote'
	},
	"shim": {
        'lib/jquery.ui.widget': ['jquery'],
        'lib/jquery.fileupload': ['jquery','lib/jquery.ui.widget']
    }
	
});