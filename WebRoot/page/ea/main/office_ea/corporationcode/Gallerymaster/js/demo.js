/*
 * blueimp Gallery Demo JS 2.12.1
 * https://github.com/blueimp/Gallery
 *
 * Copyright 2013, Sebastian Tschan
 * https://blueimp.net
 *
 * Licensed under the MIT license:
 * http://www.opensource.org/licenses/MIT
 */

/* global blueimp, $ */

$(function () {


    // Initialize the Gallery as video carousel:
    blueimp.Gallery([
        {
            title: enName,
            href: basePath+path,
            type: 'video/mp4',
            poster: 'https://i.imgur.com/MUSw4Zu.jpg'
        }
        
    ], {
        container: '#blueimp-video-carousel',
        carousel: true
    });

});
