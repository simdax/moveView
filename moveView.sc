/*

a=MoveView().front;
a.add(Button, 50@50)

*/
MoveView : View{

	*new{arg p, b;
		^super.new(p, b).init
	}
	// interface
	add{ arg v, b;
		v.new(this, b).acceptsMouse_(false) 
	}
	//pr
	init{
		this
		.mouseDownAction_({ arg self, x, y, mod, but, nbCl;
			var v=self.children.detect{|vue| vue.bounds.contains(x@y)};
			if(v.notNil)
			{
				mod.isCtrl.if {this.moveAction(self, x, y, v)};
				mod.isShift.if {this.resizeAction(self, x, y, v)}
			}
		})
	}
	moveAction{ arg self, x, y, v;
		var xfirst = x - v.bounds.origin.x;
		var yfirst = y - v.bounds.origin.y;
		v.focus;
		self.mouseMoveAction_({ arg self, xx, yy;
			v.moveTo(xx - xfirst, yy - yfirst);
		})
	}
	resizeAction{ arg self, x, y, v;
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
	}
}