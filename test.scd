
(
(
var f;
w=FlowView(nil, 350@150)
.mouseEnterAction_{ arg self, x, y, mod, but;
	self.children.do { |x|
		x.acceptsMouse_(false)
		.addAction({
			if(m!=0){self.mouseDownAction.value(self, x, y, mod, but)};
		}, \mouseDownAction)
	};
}
.mouseDownAction_({ arg self, x, y, mod, but, nbCl;
	var v=self.children.detect{|vue| vue.bounds.contains(x@y)};
	if(v.notNil)
	{
		v.focus; v.bounds.postln;
		mod.isCtrl.if {self.moveAction(self, x, y, v)};
		mod.isShift.if {self.resizeAction(self, x, y, v)}
		{
			f.switch(
				nil, {v.acceptsMouse_(true); f=v},
				v, {},
				{f.acceptsMouse_(false); f=v.acceptsMouse_(true)}
			);
		}
	};
})
//.mouseUpAction_{arg self;	self.children.do(_.acceptsMouse_(false))}
.front;
Button(w).value_(Array.rand(20,0.0,1));
Button(w).value_(Array.rand(20,0.0,1))
)
)