#version 430 core

//in vec4 interpolatedVertexColor;
in vec3 theColor;
in vec3 theNormal;
in vec3 thePosition;

out vec4 outColor;

uniform vec3 LightPosition;

void main()
{
	vec3 lightVector = normalize(LightPosition - thePosition);
	vec3 tempColor = theColor * dot(lightVector, normalize(theNormal));
	outColor = vec4(tempColor, 1);
}
