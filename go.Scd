(
~moveAction={ arg self, x, y, v;
	var xfirst = x - v.bounds.origin.x;
	var yfirst = y - v.bounds.origin.y;
	v.focus;
	self.mouseMoveAction_({ arg self, xx, yy;
		v.moveTo(xx - xfirst, yy - yfirst);
	})
};
~resizeAction={ arg self, x, y, v;
	var xfirst = x;
	var wfirst = v.bounds.width ;
	var yfirst = y;
	var hfirst = v.bounds.height;
	v.focus;
	self.mouseMoveAction_({ arg self, xx, yy;
		v.resizeTo(
				wfirst + (xx - xfirst),
				hfirst + (yy - yfirst)				
			)
	})
};
w = View(nil, 400@400)
.mouseDownAction_({ arg self, x, y, mod, but, nbCl;
	var v=self.children.detect{|vue| vue.bounds.contains(x@y)};
	if(v.notNil)
 	{
		mod.isCtrl.if {~moveAction.(self, x, y, v)};
		mod.isShift.if {~resizeAction.(self, x, y, v)}
	}
}).front;

Button(w, 150@150).background_(Color.rand).acceptsMouse_(false);
h=Plotter(nil,  (150@150).asRect, w);//.background_(Color.rand);
h.interactionView.acceptsMouse_(false);
h.value_([0,1,2])
) 

