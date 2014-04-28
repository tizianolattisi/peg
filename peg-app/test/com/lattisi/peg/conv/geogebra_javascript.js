function ggbOnInit() {
    ggbApplet.evalCommand("A = (0, 0)");
    ggbApplet.evalCommand("B = (0, 6)");
    ggbApplet.evalCommand("C = (2, 4)");
    ggbApplet.evalCommand("AB = Segment[A, B]");
    ggbApplet.evalCommand("BC = Segment[B, C]");
    ggbApplet.evalCommand("CA = Segment[C, A]");
}