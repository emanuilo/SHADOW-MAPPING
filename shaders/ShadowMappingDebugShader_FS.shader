#version 430 core

out vec4 fragmentColor;

in vec2 textureCoords;

uniform sampler2D depthMap;

void main()
{
	float depthValue = texture(depthMap, textureCoords).r;
	fragmentColor = vec4(vec3(depthValue), 1.0);
	//fragmentColor = vec4(1.0, 0.0, 0.0, 1.0);
}
