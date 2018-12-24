#version 430 core
layout(lines) in;
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

void animateVertex(in vec4 vertex1, in vec4 vertex2, float t)
{
	vec4 vertex = mix(vertex1, vertex2, t);
	vertex = vertex/vertex.w;
	vertex.y = 0.1*cos(vertex.x) + 0.1*sin(vertex.z);
	gl_Position = VPtransform * vertex;
	EmitVertex();
}

void main()
{
	for(int i = 0; i <= 11; i++)
	{
		float t = i / 11.0;
		animateVertex(gl_in[0].gl_Position, gl_in[1].gl_Position, t);
	}

	EndPrimitive();
}