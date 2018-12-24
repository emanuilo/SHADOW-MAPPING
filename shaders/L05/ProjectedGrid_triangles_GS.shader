#version 430 core
layout(triangles) in;
layout(points, max_vertices = 20) out;

uniform mat4 VPtransform;

void subdivide(inout vec4 pt1, inout vec4 pt2, inout vec4 pt3)
{
	vec4 r1 = (pt1+pt2)/2;
	vec4 r2 = (pt3+pt2)/2;
	vec4 r3 = (pt1+pt3)/2;
	
	pt1 = r1;
	pt2 = r2;
	pt3 = r3;
}

void animateVertex(in vec4 vertex)
{
	vertex.xyz = vertex.xyz/vertex.w;
	//vertex.y = 0.2*cos(vertex.x) + 0.3*sin(vertex.z);
	vertex.w = 1;
	gl_Position = VPtransform * vertex;
	EmitVertex();
}

void main()
{
	for(int i = 0; i < 3; i++)
	{
		animateVertex(gl_in[i].gl_Position);
	}

	vec4 pt1 = gl_in[0].gl_Position;
	vec4 pt2 = gl_in[1].gl_Position;
	vec4 pt3 = gl_in[2].gl_Position;

	subdivide(pt1, pt2, pt3);

	animateVertex(pt1);
	animateVertex(pt2);
	animateVertex(pt3);

	animateVertex((gl_in[0].gl_Position + pt1)/2);
	animateVertex((gl_in[1].gl_Position + pt1)/2);
	animateVertex((gl_in[1].gl_Position + pt2)/2);
	animateVertex((gl_in[2].gl_Position + pt2)/2);
	animateVertex((gl_in[2].gl_Position + pt3)/2);
	animateVertex((gl_in[0].gl_Position + pt3)/2);
	animateVertex((pt1 + pt3)/2);
	animateVertex((pt2 + pt3)/2);
	animateVertex((pt1 + pt2)/2);

	EndPrimitive();
}