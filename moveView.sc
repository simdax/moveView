/*

a=MoveView(nil, 350@150).front;
a.addFlowLayout;
2.do{Button(a).mouseDownAction_{}};
c=MultiSliderView(a, 150@150).value_(Array.rand(20,0.0,1));

SkipJack({c.hasFocus.postln}, 0.5);

SkipJack.stopAll 

*/
MoveView : UserView{

	var f; // internal pointer
	*new{arg p, b;
		^super.new(p, b).init
	}
	//pr
	init{
		this
		.mouseEnterAction_{ arg self, x, y, mod, but;
			self.children.do { |x|
				x.acceptsMouse_(false)
				.addAction({ arg s, xx, yy, m, b;
					"alooooors ?".postln;
					if(m!=0){self.mouseDownAction.value(s, xx, yy, m, b)};
				}, \mouseDownAction)
			};
		}
		.mouseDownAction_({ arg self, x, y, mod, but, nbCl;
			var v=self.children.detect{|vue| vue.bounds.contains(x@y)};
			v.postln;
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
		.mouseUpAction_{ arg self;	self.mouseMoveAction_{} }
	}
	moveAction{ arg self, x, y, v;
		var xfirst = x - v.bounds.origin.x;
		var yfirst = y - v.bounds.origin.y;
		v.focus;
		self.mouseMoveAction_({ arg self, xx, yy;
			if(this.children.removing(v).any{|x|
				x.bounds.intersects(
					v.bounds.moveTo(xx-xfirst, yy-yfirst)
				)}.not
			)
			{v.moveTo(xx - xfirst, yy - yfirst)};
		})
	}
	resizeAction{ arg self, x, y, v;
		var xfirst = x;
		var wfirst = v.bounds.width ;
		var yfirst = y;
		var hfirst = v.bounds.height;
		var resizeAction={ arg obj, x, y;
			obj.resizeTo(
				wfirst + (x - xfirst),hfirst + (y - yfirst)
			)
		};
		v.focus;
		self.mouseMoveAction_({ arg self, xx, yy;
			if(this.children.removing(v).any{|x|
				x.bounds.intersects(
					resizeAction.(v.bounds, xx, yy)
				)}.not
			)
			{resizeAction.(v, xx, yy)}
		})
	}
}

