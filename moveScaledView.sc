MoveScaledView : ScaledViewPistes{

	*new{arg p, b;
		^super.new(p, b).initMSV
	}
	// interface
	add{ arg v, b;
		v.new(this, b).acceptsMouse_(false) 
	}
	//pr
	initMSV{
		this
		.mouseDownAction_({ arg self, x, y, mod, but, nbCl;
			// si on touche la vue
			var v=self.children.detect{|vue| vue.bounds.contains(x@y)};
			if(v.notNil){
				// Si click gauche et mod
				if(but==0)
				{
					mod.isCtrl.if {this.moveAction(self, x, y, v)};
					mod.isShift.if {this.resizeAction(self, x, y, v)}
				}
				// sinon on passe à la vue
				{
					v.mouseDownAction.value(v, x, y, mod, but, nbCl)
				}
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
		// elle est un peu modifié pour bouger que le x;
		var xfirst = x;
		var wfirst = v.bounds.width ;
		// var yfirst = y;
		 var hfirst = v.bounds.height;
		v.focus;
		self.mouseMoveAction_({ arg self, xx, yy;
			v.bounds_(v.bounds.width_(
				wfirst + (xx - xfirst)
			).postln)
		})
	}
}


(
a=MoveScaledView(nil, 300@300).front;
a.add(PbindGUI, 50@50)
)