
window.dhx = window.dhx || {};

/**
 * Diagram constructor
 * @param {string|HTMLElement} container - Container element or its ID
 * @param {Object} config - Diagram configuration
 * @constructor
 */
dhx.Diagram = function(container, config) {
    this.container = typeof container === 'string' ? document.getElementById(container) : container;
    this.config = config || {};
    this.data = new dhx.DataCollection();
    this.shapes = {};
    this.links = [];
    this.dataObj = {};
    this.init();
};

/**
 * Initialize diagram
 * @private
 */
dhx.Diagram.prototype.init = function() {
    if (!this.container) {
        console.error('Container not found');
        return;
    }

    this.container.innerHTML = '';
    this.container.classList.add('dhx_diagram');

    // Set default configuration
    this.config.type = this.config.type || 'default';
    this.config.scale = this.config.scale || 1;
    this.config.grid = this.config.grid || { size: 20, cell: 20 };

    // Create main SVG element
    this.svg = document.createElementNS('http://www.w3.org/2000/svg', 'svg');
    this.svg.setAttribute('width', '100%');
    this.svg.setAttribute('height', '100%');
    this.svg.style.overflow = 'visible';
    this.container.appendChild(this.svg);

    // Create layers
    this.layers = {
        background: document.createElementNS('http://www.w3.org/2000/svg', 'g'),
        links: document.createElementNS('http://www.w3.org/2000/svg', 'g'),
        shapes: document.createElementNS('http://www.w3.org/2000/svg', 'g'),
        controls: document.createElementNS('http://www.w3.org/2000/svg', 'g')
    };

    Object.values(this.layers).forEach(layer => this.svg.appendChild(layer));

    console.log('DHTMLX Diagram initialized successfully');
};

/**
 * Load data into diagram
 * @param {Array} data - Array of shape objects
 */
dhx.Diagram.prototype.parse = function(data) {
    if (!Array.isArray(data)) {
        console.error('Data must be an array');
        return;
    }

    this.data.parse(data);
    this.layout();
};


/**
 * Create shape element
 * @param {Object} item - Shape data
 * @returns {SVGElement} Shape element
 * @private
 */
dhx.Diagram.prototype.createShape = function(item) {
    const g = document.createElementNS('http://www.w3.org/2000/svg', 'g');
    g.setAttribute('data-id', item.id);
    g.classList.add('dhx_diagram_shape');

    // Create shape based on type
    let shapeElement;
    switch (item.shape || 'rectangle') {
        case 'rectangle':
            shapeElement = this.createRectangle(item);
            break;
        case 'ellipse':
            shapeElement = this.createEllipse(item);
            break;
        case 'triangle':
            shapeElement = this.createTriangle(item);
            break;
        default:
            shapeElement = this.createRectangle(item);
    }

    g.appendChild(shapeElement);

    // Add text
    if (item.text) {
        const text = document.createElementNS('http://www.w3.org/2000/svg', 'text');
        text.textContent = item.text;
        text.setAttribute('text-anchor', 'middle');
        text.setAttribute('dy', '0.3em');
        text.classList.add('dhx_diagram_text');
        g.appendChild(text);
    }

    // Add event listeners
    this.addEventListeners(g, item);

    return g;
};

/**
 * Create rectangle shape
 * @param {Object} item - Shape data
 * @returns {SVGElement} Rectangle element
 * @private
 */
dhx.Diagram.prototype.createRectangle = function(item) {
    const rect = document.createElementNS('http://www.w3.org/2000/svg', 'rect');
    if (item.horizontal){
        rect.setAttribute('width', item.width || 250);
        rect.setAttribute('height', item.height || 50);
    } else {
        rect.setAttribute('width', item.width || 50);
        rect.setAttribute('height', item.height || 150);
    }

    rect.setAttribute('rx', item.rx || 5);
    rect.setAttribute('ry', item.ry || 5);
    rect.setAttribute('fill', item.fill || '#ffffff');
    rect.setAttribute('stroke', item.stroke || '#000000');
    rect.setAttribute('stroke-width', item.strokeWidth || 2);

    return rect;
};

/**
 * Create ellipse shape
 * @param {Object} item - Shape data
 * @returns {SVGElement} Ellipse element
 * @private
 */
dhx.Diagram.prototype.createEllipse = function(item) {
    const ellipse = document.createElementNS('http://www.w3.org/2000/svg', 'ellipse');
    const width = item.width || 120;
    const height = item.height || 60;
    ellipse.setAttribute('rx', width / 2);
    ellipse.setAttribute('ry', height / 2);
    ellipse.setAttribute('fill', item.fill || '#ffffff');
    ellipse.setAttribute('stroke', item.stroke || '#000000');
    ellipse.setAttribute('stroke-width', item.strokeWidth || 2);

    return ellipse;
};

/**
 * Create triangle shape
 * @param {Object} item - Shape data
 * @returns {SVGElement} Polygon element
 * @private
 */
dhx.Diagram.prototype.createTriangle = function(item) {
    const width = item.width || 120;
    const height = item.height || 60;
    const points = [
        `0,${height}`,
        `${width / 2},0`,
        `${width},${height}`
    ].join(' ');

    const polygon = document.createElementNS('http://www.w3.org/2000/svg', 'polygon');
    polygon.setAttribute('points', points);
    polygon.setAttribute('fill', item.fill || '#ffffff');
    polygon.setAttribute('stroke', item.stroke || '#000000');
    polygon.setAttribute('stroke-width', item.strokeWidth || 2);

    return polygon;
};

/**
 * Create links between shapes
 * @private
 */
dhx.Diagram.prototype.createLinks = function() {
    const data = this.data.serialize();

    data.forEach(item => {
        if (item.parent) {
            const link = this.createLink(item.id, item.parent);
            if (link) {
                this.links.push(link);
                this.layers.links.appendChild(link);
            }
        }
    });
};

/**
 * Create link between two shapes
 * @param {string} sourceId - Source shape ID
 * @param {string} targetId - Target shape ID
 * @returns {SVGElement} Link element
 * @private
 */
dhx.Diagram.prototype.createLink = function(sourceId, targetId) {
    const source = this.shapes[sourceId];
    const target = this.shapes[targetId];

    if (!source || !target) return null;

    const path = document.createElementNS('http://www.w3.org/2000/svg', 'path');
    path.setAttribute('fill', 'none');
    path.setAttribute('stroke', '#000000');
    path.setAttribute('stroke-width', 2);
    path.setAttribute('marker-end', 'url(#arrow)');
    path.setAttribute('data-source',sourceId);
    path.setAttribute('data-target',targetId);
    return path;
};

/**
 * Apply organization chart layout
 * @private
 */
dhx.Diagram.prototype.applyOrgLayout = function() {
    const data = this.data.serialize();
    const levels = this.buildHierarchy(data);

    let y = 50;
    let widthPX = 50;
    let heightPX = 50;
    levels.forEach(level => {
        const levelWidth = level.reduce((width, item) => {
            const shape = this.shapes[item.id];
            const rect = shape.querySelector('rect, ellipse, polygon');
            const shapeWidth = parseInt(rect.getAttribute('width') || rect.getAttribute('rx') * 2 || 120);
            return width + shapeWidth + 40;
        }, 0);

        //let x = (this.container.clientWidth - levelWidth) / 2;
        let x = 50;
        let parentX = 0;
        level.forEach(item => {
            const shape = this.shapes[item.id];
            const rect = shape.querySelector('rect, ellipse, polygon');
            const shapeWidth = parseInt(rect.getAttribute('width') || rect.getAttribute('rx') * 2 || 120);
            const shapeHeight = parseInt(rect.getAttribute('height') || rect.getAttribute('ry') * 2 || 60);

            const parentItem = this.shapes[item.parent];
            if (parentItem !== undefined){
                let paX = Number(parentItem.getAttribute("x"));
                let paY = Number(parentItem.getAttribute("y"));
                if (parentX !== paX){
                    parentX = paX;
                    x = paX;
                    const childNum = this.dataObj[item.parent]["childNum"];
                    const length = (childNum + (childNum - 1) - 1)/2;
                    paX += length*50;
                    /*if (this.dataObj[item.parent]["horizontal"]){
                        let id = this.container["id"];
                        let num = $("#" + id).width();
                        paX = (Math.round(num / 100) * 100)/2;
                    }*/
                    parentItem.setAttribute('transform', `translate(${paX}, ${paY})`);

                }

            }


            x += length * 50;
            shape.setAttribute('transform', `translate(${x}, ${y})`);
            shape.setAttribute('x', x);
            shape.setAttribute('y', y);
            if (widthPX < x){
                widthPX = x;
            }
            if (heightPX < y){
                heightPX = y;
            }
            // Position text
            const text = shape.querySelector('text');
            if (text) {
                text.setAttribute('x', shapeWidth / 2);
                text.setAttribute('y', shapeHeight / 2);
                if (item.horizontal){
                    text.style.writingMode = 'horizontal-tb';
                    this.dataObj[item.id]["horizontal"]= true;
                }
            }
            //x += shapeWidth + 40;
            const childNum = item.childNum;
            if (childNum > 1){
                x += shapeWidth + (childNum * 50) + (childNum-1) * 50;
                //x += shapeWidth + length * 50;
            } else {
                x += shapeWidth + childNum * 50
            }


        });
        y += 200;
    });

    let id = this.container["id"];
    let width = widthPX + 100;
    if (width > $("#" + id).width()){
        $("#" + id).width(width);
    }
    let height = heightPX + 200;
    if (height > $("#" + id).height()){
        $("#" + id).height( height);
    }
};

/**
 * Apply default layout
 * @private
 */
dhx.Diagram.prototype.applyDefaultLayout = function() {
    const data = this.data.serialize();

    data.forEach((item, index) => {
        const shape = this.shapes[item.id];
        if (!shape) return;

        const rect = shape.querySelector('rect, ellipse, polygon');
        const shapeWidth = parseInt(rect.getAttribute('width') || rect.getAttribute('rx') * 2 || 120);
        const shapeHeight = parseInt(rect.getAttribute('height') || rect.getAttribute('ry') * 2 || 60);

        const x = 50 + (index % 4) * 200;
        const y = 50 + Math.floor(index / 4) * 150;

        shape.setAttribute('transform', `translate(${x}, ${y})`);

        // Position text
        const text = shape.querySelector('text');
        if (text) {
            text.setAttribute('x', shapeWidth / 2);
            text.setAttribute('y', shapeHeight / 2);
        }
    });

};

/**
 * Build hierarchy levels
 * @param {Array} data - Shape data
 * @returns {Array} Hierarchy levels
 * @private
 */
dhx.Diagram.prototype.buildHierarchy = function(data) {
    let length = data.length;
    const dataObj = {};
    let maxLength = 0
    for (let i = length - 1; i > -1; i--){
        const d = data[i];
        const parentId = d["parent"];
        const id = d["id"];
        if (d["childNum"] === undefined){
            d["childNum"] = 1;
        }
        if (dataObj[id] == null || dataObj[id]["childNum"] === undefined){
            d["childNum"] = 1;
        } else {
            d["childNum"] = dataObj[id]["childNum"];

        }
        if (dataObj[parentId] == null || dataObj[parentId]["childNum"] === undefined){
            dataObj[parentId] = {"id":parentId};
            dataObj[parentId]["childNum"] =  1;

        } else {
            dataObj[parentId]["childNum"] += d["childNum"];
            if (maxLength < dataObj[parentId]["childNum"]){
                maxLength = dataObj[parentId]["childNum"];
            }
        }

    }
    this.dataObj = dataObj;
    const levels = [];
    const rootNodes = data.filter(item => !item.parent);

    if (rootNodes.length === 0) return levels;

    levels.push(rootNodes);

    let currentLevel = rootNodes;

    while (currentLevel.length > 0) {
        const nextLevel = [];
        currentLevel.forEach(node => {
            const children = data.filter(item => item.parent === node.id);
            nextLevel.push(...children);
        });

        if (nextLevel.length > 0) {
            levels.push(nextLevel);
        }

        currentLevel = nextLevel;
    }


    return levels;
};

/**
 * Update link paths
 * @private
 */
dhx.Diagram.prototype.updateLinks = function() {
    if (this.links.length === 0){
        const data = this.data.serialize();
        data.forEach(item => {
            if (item.parent) {
                const link = this.createLink(item.id, item.parent);
                if (link) {
                    const sourceId = link.getAttribute('data-source');
                    const targetId = link.getAttribute('data-target');

                    if (!sourceId || !targetId) return;

                    const source = this.shapes[sourceId];
                    const target = this.shapes[targetId];

                    if (!source || !target) return;

                    const sourceRect = source.getBoundingClientRect();
                    const targetRect = target.getBoundingClientRect();
                    const sourceX = sourceRect.left + sourceRect.width / 2;
                    const sourceY = sourceRect.top - 49;
                    // const sourceY = sourceRect.top - sourceRect.height - sourceRect.height / 2;
                    const targetX = targetRect.left + targetRect.width / 2;
                    //const targetY = targetRect.top + targetRect.height/2;
                    const targetY = targetRect.top + targetRect.height - 49;
                    if (sourceY !== targetY){
                        var path = document.createElementNS('http://www.w3.org/2000/svg', 'path');
                        path.setAttribute('fill', 'none');
                        //path.setAttribute('stroke', '#000000');
                        path.setAttribute('stroke', '#000000');
                        path.setAttribute('stroke-width', 2);
                        path.setAttribute('marker-end', 'url(#arrow)');
                        let d = `M${targetX},${targetY} L${targetX},${targetY + 15}`;
                        var pathElement = document.querySelector('path');
                        path.setAttribute('d', d);
                        this.links.push(path);
                        this.layers.links.appendChild(path);
                        path = document.createElementNS('http://www.w3.org/2000/svg', 'path');
                        path.setAttribute('fill', 'none');
                        //path.setAttribute('stroke', '#000000');
                        path.setAttribute('stroke', '#000000');
                        path.setAttribute('stroke-width', 2);
                        path.setAttribute('marker-end', 'url(#arrow)');
                        d = `M${sourceX},${targetY + 15} L${targetX},${targetY + 15}`;
                        path.setAttribute('d', d);
                        this.links.push(link);
                        this.layers.links.appendChild(path);
                        path = document.createElementNS('http://www.w3.org/2000/svg', 'path');
                        path.setAttribute('fill', 'none');
                        //path.setAttribute('stroke', '#000000');
                        path.setAttribute('stroke', '#000000');
                        path.setAttribute('stroke-width', 2);
                        path.setAttribute('marker-end', 'url(#arrow)');
                        d = `M${sourceX},${sourceY} L${sourceX},${targetY + 15}`;
                        path.setAttribute('d', d);
                        this.layers.links.appendChild(path);

                    } else {
                        const d = `M${sourceX},${sourceY} L${targetX},${targetY}`;
                        link.setAttribute('d', d);
                        this.links.push(link);
                        this.layers.links.appendChild(link);
                    }

                }
            }
        });
    } else {
        this.links.forEach(link => {
            updatePathD();
        });
    }

};
function updatePathD(){
    const sourceId = link.getAttribute('data-source');
    const targetId = link.getAttribute('data-target');

    if (!sourceId || !targetId) return;

    const source = this.shapes[sourceId];
    const target = this.shapes[targetId];

    if (!source || !target) return;

    const sourceRect = source.getBoundingClientRect();
    const targetRect = target.getBoundingClientRect();
    const sourceX = sourceRect.left + sourceRect.width / 2;
    const sourceY = sourceRect.top - 49;
    // const sourceY = sourceRect.top - sourceRect.height - sourceRect.height / 2;
    const targetX = targetRect.left + targetRect.width / 2;
    //const targetY = targetRect.top + targetRect.height/2;
    const targetY = targetRect.top + targetRect.height - 49;
    const d = `M${sourceX},${sourceY} L${targetX},${targetY}`;
    link.setAttribute('d', d);
}
/**
 * Add event listeners to shape
 * @param {SVGElement} shape - Shape element
 * @param {Object} item - Shape data
 * @private
 */
dhx.Diagram.prototype.addEventListeners = function(shape, item) {
    shape.addEventListener('click', (e) => {
        this.fireEvent('click', {
            id: item.id,
            target: shape,
            event: e
        });
    });

    shape.addEventListener('dblclick', (e) => {
        this.fireEvent('dblclick', {
            id: item.id,
            target: shape,
            event: e
        });
    });
};

/**
 * Clear diagram
 * @private
 */
dhx.Diagram.prototype.clear = function() {
    this.layers.shapes.innerHTML = '';
    this.layers.links.innerHTML = '';
    this.shapes = {};
    this.links = [];
};

/**
 * Layout diagram
 */
dhx.Diagram.prototype.layout = function() {
    this.clear();
    const data = this.data.serialize();
    // Create shapes
    data.forEach(item => {
        const shape = this.createShape(item);
        if (shape) {
            this.shapes[item.id] = shape;
            this.layers.shapes.appendChild(shape);
        }
    });


    if (this.config.type === 'org') {
        this.applyOrgLayout();
        setTimeout(() => {
            this.updateLinks();
        }, 500);
    } else {
        this.applyDefaultLayout();
    }
};

/**
 * Export diagram as PNG
 * @returns {string} Data URL of the image
 */
dhx.Diagram.prototype.export = function() {
    return {
        png: () => {
            const svgData = new XMLSerializer().serializeToString(this.svg);
            const canvas = document.createElement('canvas');
            const ctx = canvas.getContext('2d');
            const img = new Image();

            canvas.width = this.svg.clientWidth;
            canvas.height = this.svg.clientHeight;

            img.onload = function() {
                ctx.drawImage(img, 0, 0);
                const pngData = canvas.toDataURL('image/png');
                const downloadLink = document.createElement('a');
                downloadLink.href = pngData;
                downloadLink.download = 'diagram.png';
                downloadLink.click();
            };

            img.src = 'data:image/svg+xml;base64,' + btoa(unescape(encodeURIComponent(svgData)));

            return img.src;
        }
    };
};

/**
 * Set shape style
 * @param {string} id - Shape ID
 * @param {Object} style - Style object
 */
dhx.Diagram.prototype.setShapeStyle = function(id, style) {
    const shape = this.shapes[id];
    if (!shape) return;

    const shapeElement = shape.querySelector('rect, ellipse, polygon');
    if (!shapeElement) return;

    Object.keys(style).forEach(prop => {
        shapeElement.setAttribute(prop, style[prop]);
    });
};

/**
 * Events system
 */
dhx.Diagram.prototype.events = {};

/**
 * Attach event handler
 * @param {string} name - Event name
 * @param {Function} handler - Event handler
 */
dhx.Diagram.prototype.attachEvent = function(name, handler) {
    if (!this.events[name]) {
        this.events[name] = [];
    }
    this.events[name].push(handler);
    return this.events[name].length - 1;
};

/**
 * Detach event handler
 * @param {string} name - Event name
 * @param {number} id - Handler ID
 */
dhx.Diagram.prototype.detachEvent = function(name, id) {
    if (this.events[name] && this.events[name][id]) {
        this.events[name][id] = null;
    }
};

/**
 * Fire event
 * @param {string} name - Event name
 * @param {Object} data - Event data
 * @private
 */
dhx.Diagram.prototype.fireEvent = function(name, data) {
    if (!this.events[name]) return;

    this.events[name].forEach(handler => {
        if (handler) {
            handler(data);
        }
    });
};

/**
 * DataCollection class for data management
 * @constructor
 */
dhx.DataCollection = function() {
    this.items = [];
};

/**
 * Parse data into collection
 * @param {Array} data - Data array
 */
dhx.DataCollection.prototype.parse = function(data) {
    this.items = Array.isArray(data) ? data : [];
};

/**
 * Serialize collection data
 * @returns {Array} Data array
 */
dhx.DataCollection.prototype.serialize = function() {
    return this.items;
};

// Add CSS styles
const style = document.createElement('style');
style.textContent = `

`;
document.head.appendChild(style);

